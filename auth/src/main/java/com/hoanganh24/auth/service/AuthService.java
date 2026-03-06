package com.hoanganh24.auth.service;

import com.nimbusds.jwt.SignedJWT;

import javax.naming.AuthenticationException;

public interface AuthService {
    SignedJWT verifyToken(String token) throws AuthenticationException;
    void register(String email, String password);
}
