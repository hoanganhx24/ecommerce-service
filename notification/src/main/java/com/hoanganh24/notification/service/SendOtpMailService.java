package com.hoanganh24.notification.service;

import com.hoanganh24.notification.dto.request.SendOtpRequest;

public interface SendOtpMailService {
    void sendOtpSignUp(SendOtpRequest request);
}
