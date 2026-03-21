package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.response.UserResponse;
import com.hoanganh24.auth.exception.AuthenticationException;
import com.hoanganh24.auth.mapper.UserMapper;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Invalid email"));

        if (!user.getIsActive()) {
            throw new AuthenticationException("Account is not active");
        }

        if (!matchesPassword(password, user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        return userMapper.toUserResponse(user);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
