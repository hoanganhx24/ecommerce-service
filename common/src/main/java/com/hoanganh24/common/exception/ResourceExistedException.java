package com.hoanganh24.common.exception;

public class ResourceExistedException extends ExtendedRuntimeException {

    public ResourceExistedException(String code, String message) {
        super(code, message);
    }
}
