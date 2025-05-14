package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Product;

import java.util.List;

public interface ProductService {
    ApiResponse<List<Product>> findAll();

    ApiResponse<Product> findById(Long id);

    ApiResponse<Product> save(Product product);

    ApiResponse<Product> update(Long id, Product product);

    ApiResponse<String> deleteById(Long id);
}