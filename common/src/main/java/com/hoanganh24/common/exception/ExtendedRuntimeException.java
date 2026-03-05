package com.hoanganh24.common.exception;

public class ExtendedRuntimeException extends RuntimeException {
    private String code;
    private String message;

    public ExtendedRuntimeException() {
    }

    public ExtendedRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
