package com.hoanganh24.common.event;

public record OtpEventPayload(
        String email,
        String otp
) {}
