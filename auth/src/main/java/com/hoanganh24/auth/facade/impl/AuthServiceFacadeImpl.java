package com.hoanganh24.auth.facade.impl;

import com.hoanganh24.auth.dto.request.*;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.dto.response.UserResponse;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;
import com.hoanganh24.auth.enums.TokenType;
import com.hoanganh24.auth.exception.AuthenticationException;
import com.hoanganh24.auth.facade.AuthServiceFacade;
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
        userService.createOrUpdateInActivatedUser(signupRequest.getEmail(), signupRequest.getPassword());
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
            UserResponse user = userService.activateUser(request.getEmail());

            String accessToken = tokenService.generateToken(user, TokenType.ACCESS);
            String refreshToken = tokenService.generateToken(user, TokenType.REFRESH);

            return VerifyOtpResponse
                    .builder()
                    .token(accessToken)
                    .refreshToken(refreshToken)
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
        UserResponse user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return generateAuthResponse(user);
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

            UserResponse existingUser = userService.findByEmail(email);

            invalidateTokenService.create(signed.getJWTClaimsSet().getJWTID(),
                    signed.getJWTClaimsSet().getExpirationTime());

            return generateAuthResponse(existingUser);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid JWT token");
        } catch (ParseException e) {
            throw new AuthenticationException("Invalid JWT token format");
        }
    }

    // Private methods
    private AuthResponse generateAuthResponse(UserResponse user) {
        return AuthResponse.builder()
                .accessToken(tokenService.generateToken(user, TokenType.ACCESS))
                .refreshToken(tokenService.generateToken(user, TokenType.REFRESH))
                .authenticated(true)
                .build();
    }
}
