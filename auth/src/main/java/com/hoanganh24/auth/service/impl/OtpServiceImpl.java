package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.request.VerifyOtpRequest;
import com.hoanganh24.auth.dto.response.VerifyOtpResponse;
import com.hoanganh24.auth.enums.TokenType;
import com.hoanganh24.auth.event.EventPublisher;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.auth.service.TokenService;
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
    private final TokenService tokenService;
    private final UserRepository userRepository;

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
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        String key = STR."auth:otp:\{verifyOtpRequest.getType().toString().toLowerCase()}:\{verifyOtpRequest.getEmail()}";
        String storedOtp = (String) redisUtils.getKV(key);
        if (storedOtp != null && storedOtp.equals(verifyOtpRequest.getOtp())) {
            redisUtils.delete(key);
            User user = userRepository.findByEmail(verifyOtpRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setIsActive(true);
            userRepository.save(user);
            return VerifyOtpResponse
                    .builder()
                    .token(tokenService.generateToken(user, TokenType.ACCESS))
                    .refreshToken(tokenService.generateToken(user, TokenType.REFRESH))
                    .verified(true)
                    .build();
        }
        return VerifyOtpResponse
                .builder()
                .verified(false)
                .build();
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
