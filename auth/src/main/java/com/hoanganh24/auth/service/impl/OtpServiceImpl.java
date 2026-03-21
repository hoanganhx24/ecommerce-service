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
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class OtpServiceImpl implements OtpService {
    private final RedisUtils redisUtils;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
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
        String key = STR."auth:otp:\{verifyOtpRequest.getType().toString().toLowerCase()}:\{verifyOtpRequest.getEmail()}";
        String storedOtp = (String) redisUtils.getKV(key);
        if (storedOtp != null && storedOtp.equals(verifyOtpRequest.getOtp())) {;
            // Xóa OTP sau khi xác thực thành công
            redisUtils.delete(key);
            return true;
        }
        return false;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
