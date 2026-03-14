package com.hoanganh24.notification.service.impl;

import com.hoanganh24.notification.dto.request.SendOtpRequest;
import com.hoanganh24.notification.service.EmailService;
import com.hoanganh24.notification.service.SendOtpMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SendOtpMailServiceImpl implements SendOtpMailService {
    private final EmailService emailService;

    @Override
    public void sendOtpSignUp(SendOtpRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", request.getEmail());
        map.put("otp", request.getOtp());

        String subject = "OTP for Sign Up";
        String template = "otp-signup";

        emailService.send(request.getEmail(), subject, template, map);

    }
}
