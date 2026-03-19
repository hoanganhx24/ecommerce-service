package com.hoanganh24.auth.service;

import com.hoanganh24.auth.model.User;

public interface AuthService {
    User authenticate(String username, String password);
}
