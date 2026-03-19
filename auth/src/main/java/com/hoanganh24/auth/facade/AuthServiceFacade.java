package com.hoanganh24.auth.facade;

import com.hoanganh24.auth.dto.request.*;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.dto.response.SignupResponse;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;

public interface AuthServiceFacade {
    SignupResponse signup(SignupRequest request);
    VerifyOtpResponse verifyOtp(VerifyOtpRequest request);
    AuthResponse login(LoginRequest loginRequest);
    void logout(LogoutRequest logoutRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
