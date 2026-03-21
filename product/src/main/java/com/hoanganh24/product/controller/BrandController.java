package com.hoanganh24.product.controller;

import com.hoanganh24.common.dto.response.BaseResponse;
import com.hoanganh24.common.util.ResponseUtils;
import com.hoanganh24.product.dto.request.BrandRequest;
import com.hoanganh24.product.dto.response.BrandResponse;
import com.hoanganh24.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BaseResponse<BrandResponse>> create(@Valid @RequestBody BrandRequest request) {
        return ResponseUtils.created(brandService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<BrandResponse>> update(@Valid @RequestBody BrandRequest request, @PathVariable("id") String id) {
        return ResponseUtils.success("Update brand successfully", brandService.update(id, request));
    }

    @GetMapping("/{name}")
    public ResponseEntity<BaseResponse<BrandResponse>> getByName(@PathVariable("name") String name) {
        return ResponseUtils.success(brandService.getByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteById(@PathVariable("id") String id) {
        brandService.deleteById(id);
        return ResponseUtils.success();
    }
}
