package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.request.LoginRequest;
import com.hoanganh24.auth.dto.request.LogoutRequest;
import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.LoginResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest signupRequest);
    AuthResponse login(LoginRequest loginRequest);
    void logout(LogoutRequest logoutRequest);
}
