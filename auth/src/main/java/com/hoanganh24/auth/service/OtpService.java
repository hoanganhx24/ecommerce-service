package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.request.VerifyOtpRequest;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;

public interface OtpService {
    void sendOtp(String email);
    boolean verifyOtp(VerifyOtpRequest verifyOtpRequest);
}
