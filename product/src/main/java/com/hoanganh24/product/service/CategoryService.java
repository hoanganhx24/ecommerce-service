package com.hoanganh24.product.service;


import com.hoanganh24.product.dto.request.CategoryRequest;
import com.hoanganh24.product.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(String id, CategoryRequest request);
    void deleteById(String id);
}
