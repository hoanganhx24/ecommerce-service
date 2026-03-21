package com.hoanganh24.product.repository;

import com.hoanganh24.product.model.Brand;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends BaseRepository<Brand, String> {
    boolean existsByName(String name);
    Brand findByName(String name);
}
