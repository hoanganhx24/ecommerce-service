package com.hoanganh24.auth.dto.request;

import com.hoanganh24.auth.enums.OtpType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VerifyOtpRequest {
    @NotBlank(message = "Email khong được để trống")
    String email;

    @NotBlank(message = "OTP khong được để trống")
    String otp;

    @NotBlank(message = "Type khong được để trống")
    OtpType type;
}
