package com.hoanganh24.auth.service.impl;

import com.hoanganh24.auth.service.TokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.text.ParseException;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.signerKey}")
    private String signerKey;

    @Value("${jwt.valid-duration}")
    private long validDuration;

    @Value("${jwt.refeshable-duration}")
    private long refreshableDuration;

    @Override
    public SignedJWT verifyToken(String token) throws AuthenticationException {
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

//            if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
//                throw  new AuthenticationException("Token has expired");
//            }
            return signedJWT;
        } catch (ParseException e) {
            throw new AuthenticationException("Invalid JWT token format");
        } catch (JOSEException e) {
            throw new AuthenticationException("Error verifying JWT token");
        }
    }
}
