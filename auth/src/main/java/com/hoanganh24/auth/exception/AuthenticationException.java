package com.hoanganh24.auth.exception;

import com.hoanganh24.common.exception.ExtendedRuntimeException;

public class AuthenticationException  extends ExtendedRuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
