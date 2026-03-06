package com.hoanganh24.common.util;

import com.hoanganh24.common.dto.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static <T> ResponseEntity<BaseResponse<T>> success() {
        return ResponseEntity.ok(BaseResponse.ok());
    }

    public static <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(BaseResponse.ok(data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(BaseResponse.ok(data, message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.of(HttpStatus.CREATED.value(), "Success", data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.badRequest(message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(String message) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.error(message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> of(int status, String message) {
        return ResponseEntity.ok(BaseResponse.of(status, message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> of(int status, String message, T data) {
         return ResponseEntity.ok(BaseResponse.of(status, message, data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ofPage(Page<T> page) {
        return ResponseEntity.ok(BaseResponse.ofPage(page));
    }
}
