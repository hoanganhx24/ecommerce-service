package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.repository.UserRepository;
import com.hoanganh24.auth.service.AuthService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Server
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public void register(String email, String password) {
    }
}
