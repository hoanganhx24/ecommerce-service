package com.hoanganh24.auth.service;

import com.hoanganh24.auth.model.User;

public interface UserService {
    void createUser(String email, String password);
    void createOrUpdateInActivatedUser(String email, String password);
    User activateUser(String email);
    User updatePassword(String email, String newPassword);
    User findByEmail(String email);
}
