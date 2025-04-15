package com.example.backend.repository;

import com.example.backend.entity.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesProductRepository extends JpaRepository<CategoryProduct, Long> {
}
