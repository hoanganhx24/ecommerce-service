package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.exception.AuthenticationException;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.InvalidateTokenRepository;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.AuthService;
import com.hoanganh24.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(
                        "Invalid email or password"
                ));

        if (!user.getIsActive()) {
            throw new AuthenticationException("Account is not active");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        return user;
    }
}
