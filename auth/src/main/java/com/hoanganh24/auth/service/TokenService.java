package com.hoanganh24.auth.service;

import com.nimbusds.jwt.SignedJWT;

import javax.naming.AuthenticationException;

public interface TokenService {
    SignedJWT verifyToken(String token) throws AuthenticationException;
}
