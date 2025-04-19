package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryNew;

import java.util.List;

public interface CategoryNewService {
    ApiResponse<List<CategoryNew>> findAll();

    ApiResponse<CategoryNew> findById(Long id);

    ApiResponse<CategoryNew> save(CategoryNew categoryNew);

    ApiResponse<CategoryNew> update(Long id, CategoryNew categoryNew);

    ApiResponse<String> deleteById(Long id);
}
