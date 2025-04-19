package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.ImageVarian;

import java.util.List;

public interface ImageVarianService {
    ApiResponse<List<ImageVarian>> findAll();
    ApiResponse<ImageVarian> findById(Long id);
    ApiResponse<ImageVarian> save(ImageVarian imageVarian);
    ApiResponse<ImageVarian> update(Long id, ImageVarian imageVarian);
    ApiResponse<String> deleteById(Long id);
}
