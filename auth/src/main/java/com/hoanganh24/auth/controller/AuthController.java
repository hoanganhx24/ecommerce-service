package com.hoanganh24.auth.controller;

import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.service.AuthService;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.common.dto.response.BaseResponse;
import com.hoanganh24.common.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        otpService.sendOtp(signupRequest.getEmail());
        return ResponseUtils.success();
    }
}
