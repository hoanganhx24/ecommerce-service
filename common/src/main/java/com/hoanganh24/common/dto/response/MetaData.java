package com.hoanganh24.common.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetaData {
    int totalPages;
    int currentPage;
    long pageSize;
    long totalItems;
}
