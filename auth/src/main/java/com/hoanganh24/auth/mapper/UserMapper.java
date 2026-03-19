package com.hoanganh24.auth.mapper;

import com.hoanganh24.auth.dto.response.UserResponse;
import com.hoanganh24.auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
