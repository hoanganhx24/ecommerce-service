package com.hoanganh24.common.exception;

public class NotFoundException extends ExtendedRuntimeException {

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}

