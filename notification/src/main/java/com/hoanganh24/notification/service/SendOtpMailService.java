package com.hoanganh24.notification.service;

public interface SendOtpMailService {
    void sendOtpSignUp(String email, String otp);
}
