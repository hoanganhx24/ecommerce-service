package com.hoanganh24.common.exception;

public class BadRequestException extends ExtendedRuntimeException {
    public BadRequestException(String code, String message) {
        super(code, message);
    }
}
