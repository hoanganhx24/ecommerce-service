package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.dto.response.SignupResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest signupRequest);
}
