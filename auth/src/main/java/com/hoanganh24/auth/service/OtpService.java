package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.request.VerifyOtpRequest;

public interface OtpService {
    void sendOtp(String email);
    boolean verifyOtp(VerifyOtpRequest verifyOtpRequest);
}
