package com.hoanganh24.common.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse<T> implements Serializable {
    int status;
    String message;
    T data;
    Map<String, Object> metadata;

    public static <T> BaseResponse<T> ok() {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success");
        return response;
    }

    public static <T> BaseResponse<T> ok(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success");
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> ok(T Data, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(message);
        response.setData(Data);
        return response;
    }

    public static <T> BaseResponse<T> error(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        return response;
    }

    public static <T> BaseResponse<T> badRequest(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        return response;
    }

    public static <T> BaseResponse<T> of(int status, String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(status);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> of(int status, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

    public static <T> BaseResponse<T> ofPage(Page<T> pageResult) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("successfully");
        response.setData((T) pageResult.getContent());

        // Adding metadata
        Map<String, Object> metadata = new java.util.HashMap<>();
        metadata.put("page", pageResult.getNumber());
        metadata.put("size", pageResult.getSize());
        metadata.put("totalPages", pageResult.getTotalPages());
        metadata.put("totalElements", pageResult.getTotalElements());
        metadata.put("first", pageResult.isFirst());
        metadata.put("last", pageResult.isLast());
        metadata.put("hasNext", pageResult.hasNext());
        metadata.put("hasPrevious", pageResult.hasPrevious());
        response.setMetadata(metadata);

        return response;
    }
}
