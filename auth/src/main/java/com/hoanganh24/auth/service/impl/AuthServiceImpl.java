package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.request.SignupRequest;
import com.hoanganh24.auth.dto.response.SignupResponse;
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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        Optional<User> userOpt = userRepository.findByEmail(signupRequest.getEmail());

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            if (user.getIsActive()) {
                throw new ResourceExistedException("User is already active");
            }

            // user tồn tại nhưng chưa verify
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            userRepository.save(user);

        } else {

            // tạo user mới
            User newUser = User.builder()
                    .email(signupRequest.getEmail())
                    .role(Role.USER)
                    .isActive(false)
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .build();

            userRepository.save(newUser);
        }

        try {
            // gửi OTP
            otpService.sendOtp(signupRequest.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP");
        }
        return SignupResponse.builder()
                .email(signupRequest.getEmail())
                .otpSent(true)
                .build();
    }
}
