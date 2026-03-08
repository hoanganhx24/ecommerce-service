package com.hoanganh24.notification.service;

import java.util.Map;

public interface EmailService {
    void send(String to, String subject, String template, Map<String, Object> data);
}
