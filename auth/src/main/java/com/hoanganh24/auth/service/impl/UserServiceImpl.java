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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(String email, String password) {
        Optional<User> existingUserOpt = userRepository.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (existingUser.getIsActive()) {
                throw new ResourceExistedException(String.format("Tài khoản đã tồn tại và đang hoạt động với email: %s", email));
            }
            existingUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(existingUser);
            return;
        }
        String encodedPassword = passwordEncoder.encode(password);

        // Tạo user mới
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .isActive(false)
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void activateUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceExistedException(String.format("User với email %s không tồn tại", email)));
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại với email: " + email));
    }
}
