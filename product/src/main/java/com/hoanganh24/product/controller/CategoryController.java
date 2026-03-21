package com.hoanganh24.product.controller;

import com.hoanganh24.common.dto.response.BaseResponse;
import com.hoanganh24.common.util.ResponseUtils;
import com.hoanganh24.product.dto.request.CategoryRequest;
import com.hoanganh24.product.dto.response.CategoryResponse;
import com.hoanganh24.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseUtils.created(categoryService.create(categoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> update(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable("id") String id) {
        return ResponseUtils.success("Update category successfully", categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteById(@PathVariable("id") String id) {
        categoryService.deleteById(id);
        return ResponseUtils.success();
    }
}
