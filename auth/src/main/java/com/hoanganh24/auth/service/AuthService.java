package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.response.UserResponse;
import com.hoanganh24.auth.model.User;

public interface AuthService {
    UserResponse authenticate(String username, String password);
}
