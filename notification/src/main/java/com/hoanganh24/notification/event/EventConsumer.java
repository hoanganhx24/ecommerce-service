package com.hoanganh24.notification.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanganh24.common.constant.NotificationConstant;
import com.hoanganh24.common.event.CommandPayload;
import com.hoanganh24.common.event.OtpEventPayload;
import com.hoanganh24.notification.dto.request.SendOtpRequest;
import com.hoanganh24.notification.service.SendOtpMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final SendOtpMailService sendOtpMailService;
    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<CommandPayload> notificationConsume() {
        return payload -> {
            switch (payload.getType()) {
                case NotificationConstant.OTP_SIGNUP -> {
                    try {
                        OtpEventPayload otpEventPayload =
                                objectMapper.convertValue(
                                        payload.getData(),
                                        OtpEventPayload.class);
                        sendOtpMailService.sendOtpSignUp(SendOtpRequest
                                .builder()
                                .otp(otpEventPayload.otp())
                                .email(otpEventPayload.email())
                                .build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
