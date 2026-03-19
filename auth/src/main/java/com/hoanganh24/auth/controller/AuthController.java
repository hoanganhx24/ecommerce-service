package com.hoanganh24.auth.controller;

import com.hoanganh24.auth.dto.request.*;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;
import com.hoanganh24.auth.facade.AuthServiceFacade;
import com.hoanganh24.common.dto.response.BaseResponse;
import com.hoanganh24.common.util.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceFacade authServiceFacade;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseUtils.success(authServiceFacade.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseUtils.success(authServiceFacade.login(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<AuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseUtils.success(authServiceFacade.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        authServiceFacade.logout(logoutRequest);
        return ResponseUtils.success();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<BaseResponse<VerifyOtpResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        return ResponseUtils.success(authServiceFacade.verifyOtp(verifyOtpRequest));
    }
}
