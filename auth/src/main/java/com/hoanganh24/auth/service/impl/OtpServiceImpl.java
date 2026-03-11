package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;

@RequiredArgsConstructor
@Service
public class OtpServiceImpl implements OtpService {
    private final StreamBrige streamBrige;

    @Override
    public boolean sendOtp(String email) {
        String otp = 123456 +"";
        return false;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return false;
    }
}
