package com.hoanganh24.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SignupRequest {
    @NotBlank(message = "Email không được để trống")
    String email;

    @NotBlank(message = "Password không được để trống")
    String password;
}
