package com.hoanganh24.auth.service;

public interface OtpService {
    boolean sendOtp(String email);
    boolean verifyOtp(String email, String otp);
}
