package com.hoanganh24.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthResponse {
    String accessToken;
    String refreshToken;
    boolean authenticated;
}
