package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.enums.Role;
import com.hoanganh24.auth.model.User;
import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.AuthService;
import com.hoanganh24.auth.service.OtpService;
import com.hoanganh24.common.exception.ResourceExistedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        User user = userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
        if (user != null) {
            if (user.getIsActive()){
                throw new ResourceExistedException("User is already active");
            }
            else {
                otpService.sendOtp(signupRequest.getEmail());
                return;
            }
        }
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User newUser = User.builder()
                .email(signupRequest.getEmail())
                .role(Role.USER)
                .isActive(false)
                .password(encodedPassword)
                .build();
        userRepository.save(newUser);
    }
}
