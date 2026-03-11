package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.request.SignupRequest;

public interface AuthService {
    void signup(SignupRequest signupRequest);
}
