package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.dto.response.UserResponse;
import com.hoanganh24.auth.enums.TokenType;
import com.hoanganh24.auth.exception.AuthenticationException;

import com.hoanganh24.auth.service.InvalidateTokenService;
import com.hoanganh24.auth.service.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;

    @NonFinal
    @Value("${jwt.valid-duration}")
    private long validDuration;

    @NonFinal
    @Value("${jwt.refeshable-duration}")
    private long refreshableDuration;

    private final InvalidateTokenService invalidateTokenService;

    @Override
    @Transactional(readOnly = true)
    public SignedJWT verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            boolean isSignatureValid = signedJWT.verify(verifier);
            if (!isSignatureValid) {
                throw new AuthenticationException("Invalid JWT signature");
            }

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            if ((expiryTime == null || !expiryTime.after(new Date()))) {
                throw new AuthenticationException("Expired JWT token");
            }

            if (invalidateTokenService.existById(signedJWT.getJWTClaimsSet().getJWTID())) {
                throw  new AuthenticationException("Token has expired");
            }
            return signedJWT;
        } catch (ParseException e) {
            throw new AuthenticationException("Invalid JWT token format");
        } catch (JOSEException e) {
            throw new AuthenticationException("Error verifying JWT token");
        }
    }

    @Override
    public String generateToken(UserResponse user, TokenType tokenType) {
        long expiration =switch (tokenType) {
            case ACCESS -> validDuration;
            case REFRESH -> refreshableDuration;
        };
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("e-commerce")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus( expiration, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", (user.getRole() != null) ? user.getRole().toString() : "USER")
                .claim("userId", user.getId())
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AuthenticationException(String.format("Failed to sign JWT token: %s", e.getMessage()));
        }
    }

}
