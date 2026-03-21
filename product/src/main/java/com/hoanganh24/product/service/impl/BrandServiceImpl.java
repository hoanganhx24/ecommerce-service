package com.hoanganh24.product.service.impl;

import com.hoanganh24.common.exception.NotFoundException;
import com.hoanganh24.common.exception.ResourceExistedException;
import com.hoanganh24.product.dto.request.BrandRequest;
import com.hoanganh24.product.dto.response.BrandResponse;
import com.hoanganh24.product.mapper.BrandMapper;
import com.hoanganh24.product.model.Brand;
import com.hoanganh24.product.repository.BrandRepository;
import com.hoanganh24.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    @Transactional
    public BrandResponse create(BrandRequest request) {
        // Validate kiem tra brand ton tai
        validateBrandName(request.getName());

        Brand brand = brandMapper.toEntity(request);
        Brand saved = brandRepository.save(brand);
        return brandMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getByName(String name) {
        return brandMapper.toResponse(brandRepository.findByName(name));
    }

    @Override
    @Transactional
    public BrandResponse update(String id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id %s not found", id)));
        if (request.getName() != null && !request.getName().equals(brand.getName())) {
            validateBrandName(request.getName());
            brand.setName(request.getName());
        }
        if (request.getLogo() != null && !request.getLogo().equals(brand.getLogo())) {
            brand.setLogo(request.getLogo());
        }
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
        }
    }

    // Private Methods

    private void validateBrandName(String name) {
        if (brandRepository.existsByName(name)) {
            throw new ResourceExistedException(
                    String.format("Brand dã tồn tại với tên: %s", name)
            );
        }
    }
}
