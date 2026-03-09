package com.hoanganh24.common.exception;

public class ExtendedRuntimeException extends RuntimeException {
    private String message;

    public ExtendedRuntimeException() {
    }

    public ExtendedRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
