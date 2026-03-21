package com.hoanganh24.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {
    @NotBlank(message = "Name không được để trống")
    @Length(min = 10, message = "Name phải có ít nhất 10 ký tự")
    String name;

    @NotBlank(message = "Logo không được để trống")
    String logo;
}
