package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.service.OtpService;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {
    @Override
    public boolean sendOtp(String email) {
        return false;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return false;
    }
}
