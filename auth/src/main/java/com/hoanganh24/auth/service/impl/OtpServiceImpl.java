package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.request.VerifyOtpRequest;
import com.hoanganh24.auth.event.EventPublisher;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.auth.util.RedisUtils;
import com.hoanganh24.common.constant.NotificationConstant;
import com.hoanganh24.common.event.CommandPayload;
import com.hoanganh24.common.event.OtpEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class OtpServiceImpl implements OtpService {
    private final RedisUtils redisUtils;
    private final EventPublisher eventPublisher;

    @Override
    public void sendOtp(String email) {
        String otp = generateOtp();
        String key = "auth:otp:signup:" + email;
        redisUtils.setKVWithTtl(key, otp, Duration.ofSeconds(60));
        OtpEventPayload payload = new OtpEventPayload(email, otp);
        eventPublisher.notificationPublish(CommandPayload.builder()
                        .type(NotificationConstant.OTP_SIGNUP)
                        .data(payload).build());
    }

    @Override
    public boolean verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        return false;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
