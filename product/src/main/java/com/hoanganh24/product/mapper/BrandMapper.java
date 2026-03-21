package com.hoanganh24.product.mapper;

import com.hoanganh24.product.dto.request.BrandRequest;
import com.hoanganh24.product.dto.response.BrandResponse;
import com.hoanganh24.product.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrandMapper extends BaseMapper<Brand, BrandRequest, BrandResponse>{
}
