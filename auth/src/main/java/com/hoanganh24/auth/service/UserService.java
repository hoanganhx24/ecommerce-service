package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.response.UserResponse;
import com.hoanganh24.auth.model.User;

public interface UserService {
    void createUser(String email, String password);
    void createOrUpdateInActivatedUser(String email, String password);
    UserResponse activateUser(String email);
    UserResponse updatePassword(String email, String newPassword);
    UserResponse findByEmail(String email);
}
