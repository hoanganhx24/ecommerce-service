package com.hoanganh24.auth.dto.request;

import com.hoanganh24.auth.enums.OtpType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VerifyOtpRequest {
    String email;
    String otp;
    OtpType type;
}
