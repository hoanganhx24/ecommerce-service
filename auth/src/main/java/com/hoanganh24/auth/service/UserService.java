package com.hoanganh24.auth.service;

import com.hoanganh24.auth.model.User;

public interface UserService {
    void createUser(String email, String password);
    void activateUser(String email);
    void updatePassword(String email, String newPassword);
    User findByEmail(String email);
}
