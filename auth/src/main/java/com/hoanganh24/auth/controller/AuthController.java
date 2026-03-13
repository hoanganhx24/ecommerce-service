package com.hoanganh24.auth.controller;

import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.service.AuthService;
import com.hoanganh24.common.dto.response.BaseResponse;
import com.hoanganh24.common.util.ResponseUtils;
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

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignupResponse>> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseUtils.success(authService.signup(signupRequest));
    }
}
