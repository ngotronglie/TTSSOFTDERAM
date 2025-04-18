package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryProduct;

import java.util.List;

public interface CategoriesProductService {
    ApiResponse<List<CategoryProduct>> findAll();

    ApiResponse<CategoryProduct> findById(Long id);

    ApiResponse<CategoryProduct> save(CategoryProduct categoryProduct);

    ApiResponse<CategoryProduct> update(Long id, CategoryProduct categoryProduct);

    ApiResponse<String> deleteById(Long id);
}
