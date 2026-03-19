package com.hoanganh24.auth.service;

import com.hoanganh24.auth.dto.request.RefreshTokenRequest;
import com.hoanganh24.auth.dto.response.AuthResponse;
import com.hoanganh24.auth.enums.TokenType;
import com.hoanganh24.auth.model.User;
import com.nimbusds.jwt.SignedJWT;

import javax.naming.AuthenticationException;

public interface TokenService {
    SignedJWT verifyToken(String token);
    String generateToken(User user, TokenType tokenType);
}
