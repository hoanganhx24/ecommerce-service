package com.hoanganh24.product.mapper;

import com.hoanganh24.product.dto.request.CategoryRequest;
import com.hoanganh24.product.dto.response.CategoryResponse;
import com.hoanganh24.product.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper extends BaseMapper<Category, CategoryRequest, CategoryResponse>{
}
