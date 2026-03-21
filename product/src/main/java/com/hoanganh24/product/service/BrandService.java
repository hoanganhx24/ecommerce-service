package com.hoanganh24.product.service;

import com.hoanganh24.product.dto.request.BrandRequest;
import com.hoanganh24.product.dto.response.BrandResponse;

public interface BrandService {
    BrandResponse create(BrandRequest request);
    BrandResponse getByName(String name);
    BrandResponse update(String id, BrandRequest request);
    void deleteById(String id);
}
