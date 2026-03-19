package com.hoanganh24.auth.controller;

import com.hoanganh24.auth.dto.request.*;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;
import com.hoanganh24.auth.service.AuthService;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.auth.service.TokenService;
import com.hoanganh24.common.dto.response.BaseResponse;
import com.hoanganh24.common.util.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final OtpService otpService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseUtils.success(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseUtils.success(authService.login(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<AuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseUtils.success(tokenService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ResponseUtils.success();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<BaseResponse<VerifyOtpResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        return ResponseUtils.success(otpService.verifyOtp(verifyOtpRequest));
    }
}
