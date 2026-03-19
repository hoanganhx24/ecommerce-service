package com.hoanganh24.auth.facade.impl;

import com.hoanganh24.auth.dto.request.*;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;
import com.hoanganh24.auth.enums.TokenType;
import com.hoanganh24.auth.exception.AuthenticationException;
import com.hoanganh24.auth.facade.AuthServiceFacade;
import com.hoanganh24.auth.model.InvalidateToken;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.InvalidateTokenRepository;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.*;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;


@Service
@RequiredArgsConstructor
public class AuthServiceFacadeImpl implements AuthServiceFacade {

    private final UserService userService;
    private final OtpService otpService;
    private final TokenService tokenService;
    private final InvalidateTokenService invalidateTokenService;
    private final AuthService authService;

    @Override
    public SignupResponse signup(SignupRequest signupRequest) {
        userService.createUser(signupRequest.getEmail(), signupRequest.getPassword());
        try {
            // gửi OTP
            otpService.sendOtp(signupRequest.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP");
        }
        return SignupResponse.builder()
                .email(signupRequest.getEmail())
                .otpSent(true)
                .build();
    }

    @Override
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        if (otpService.verifyOtp(request)) {
            User user = userService.findByEmail(request.getEmail());

            userService.activateUser(request.getEmail());

            return VerifyOtpResponse
                    .builder()
                    .token(tokenService.generateToken(user, TokenType.ACCESS))
                    .refreshToken(tokenService.generateToken(user, TokenType.REFRESH))
                    .verified(true)
                    .build();
        }
        return VerifyOtpResponse
                .builder()
                .verified(false)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return AuthResponse.builder()
                .accessToken(tokenService.generateToken(user, TokenType.ACCESS))
                .refreshToken(tokenService.generateToken(user, TokenType.REFRESH))
                .authenticated(true)
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        SignedJWT signed = tokenService.verifyToken(logoutRequest.getToken());
        try {
            invalidateTokenService.create(signed.getJWTClaimsSet().getJWTID(),
                    signed.getJWTClaimsSet().getExpirationTime());
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token");
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        try {
            SignedJWT signed = tokenService.verifyToken(request.getToken());
            String email = signed.getJWTClaimsSet().getSubject();

            User existingUser = userService.findByEmail(email);

            invalidateTokenService.create(signed.getJWTClaimsSet().getJWTID(),
                    signed.getJWTClaimsSet().getExpirationTime());

            return AuthResponse.builder()
                    .accessToken(tokenService.generateToken(existingUser, TokenType.ACCESS))
                    .refreshToken(tokenService.generateToken(existingUser, TokenType.REFRESH))
                    .authenticated(true)
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid JWT token");
        } catch (ParseException e) {
            throw new AuthenticationException("Invalid JWT token format");
        }
    }
}
