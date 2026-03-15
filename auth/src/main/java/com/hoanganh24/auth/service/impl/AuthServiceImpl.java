package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.request.LoginRequest;
import com.hoanganh24.auth.dto.request.LogoutRequest;
import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.enums.Role;
import com.hoanganh24.auth.enums.TokenType;
import com.hoanganh24.auth.exception.AuthenticationException;
import com.hoanganh24.auth.model.InvalidateToken;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.InvalidateTokenRepository;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.AuthService;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.auth.service.TokenService;
import com.hoanganh24.common.exception.ResourceExistedException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final TokenService tokenService;
    private final InvalidateTokenRepository invalidateTokenRepository;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        Optional<User> userOpt = userRepository.findByEmail(signupRequest.getEmail());

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            if (user.getIsActive()) {
                throw new ResourceExistedException("User is already active");
            }

            // user tồn tại nhưng chưa verify
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            userRepository.save(user);

        } else {

            // tạo user mới
            User newUser = User.builder()
                    .email(signupRequest.getEmail())
                    .role(Role.USER)
                    .isActive(false)
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .build();

            userRepository.save(newUser);
        }

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
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found with email: " + loginRequest.getEmail()));
        if (!user.getIsActive()) {
            throw new AuthenticationException("User is not active");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }
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
            invalidateTokenRepository.save(
                    InvalidateToken.builder()
                            .id(signed.getJWTClaimsSet().getJWTID())
                            .expiryTime(signed.getJWTClaimsSet().getExpirationTime())
                            .build()
            );
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token");
        }
    }
}
