package com.hoanganh24.product.repository;

import com.hoanganh24.product.model.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category, String> {
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
}
