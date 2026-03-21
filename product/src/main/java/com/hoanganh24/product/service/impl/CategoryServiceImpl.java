package com.hoanganh24.product.service.impl;

import com.hoanganh24.common.exception.NotFoundException;
import com.hoanganh24.product.dto.request.CategoryRequest;
import com.hoanganh24.product.dto.response.CategoryResponse;
import com.hoanganh24.product.mapper.CategoryMapper;
import com.hoanganh24.product.model.Category;
import com.hoanganh24.product.repository.CategoryRepository;
import com.hoanganh24.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        validateCategoryName(request.getName());
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse update(String id, CategoryRequest request) {
        Category category = findByIdOrThrow(id);
        if (request.getName() != null && !request.getName().equals(category.getName())) {
            validateCategoryName(request.getName());
            category.setName(request.getName());
        }
        if (request.getImage() != null && !request.getImage().equals(category.getImage())) {
            category.setImage(request.getImage());
        }
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Category category = findByIdOrThrow(id);
        categoryRepository.delete(category);
    }

    // private methods
    private Category findByIdOrThrow(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", id)));
    }

    private void validateCategoryName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new NotFoundException(String.format("Category with name %s already exists", name));
        }
    }
}
