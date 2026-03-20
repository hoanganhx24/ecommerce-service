package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.enums.Role;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.UserService;
import com.hoanganh24.common.exception.ResourceExistedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        // Tạo user mới
        userRepository.save(User.builder()
                .email(email)
                .password(encodedPassword)
                .isActive(false)
                .role(Role.USER)
                .build());
    }

    @Override
    @Transactional
    public void createOrUpdateInActivatedUser(String email, String password) {
        User user = findByEmail(email);
        if (user == null) {
            createUser(email, password);
        }

        if (user.getIsActive()) {
            throw new ResourceExistedException(String.format("User với email %s đã kích hoạt", email));
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User activateUser(String email) {
        User user = findByEmail(email);
        user.setIsActive(true);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updatePassword(String email, String newPassword) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new com.hoanganh24.auth.exception.AuthenticationException("User not found with email: " + email));
    }
}
