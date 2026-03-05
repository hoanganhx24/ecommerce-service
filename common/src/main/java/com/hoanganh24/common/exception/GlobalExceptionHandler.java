package com.hoanganh24.common.exception;
import com.hoanganh24.common.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.of(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Object>> handleBadRequestException(BadRequestException ex) {
        log.error("BadRequestException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ResourceExistedException.class)
    public  ResponseEntity<BaseResponse<Object>> handleResourceExistedException(ResourceExistedException ex) {
        log.error("ResourceExistedException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BaseResponse.of(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String messsage =error.getDefaultMessage();
        });
        log.error("Validation Error: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.of(HttpStatus.BAD_REQUEST.value(), "Dữ liệu khng hợp lệ", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
        log.error("Unexpected error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Lỗi hệ thống: " + ex.getMessage()));
    }

}
