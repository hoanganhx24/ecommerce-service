package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.request.VerifyOtpRequest;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.auth.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class OtpServiceImpl implements OtpService {
    private final StreamBridge streamBrige;
    private final RedisUtils redisUtils;

    @Override
    public void sendOtp(String email) {
        String otp = generateOtp();
        String key = "auth:otp:signup:" + email;
        redisUtils.setKVWithTtl(key, otp, Duration.ofSeconds(30));
    }

    @Override
    public boolean verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return false;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
