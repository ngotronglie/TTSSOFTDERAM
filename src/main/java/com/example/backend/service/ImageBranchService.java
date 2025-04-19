package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.ImageBranch;

import java.util.List;

public interface ImageBranchService {
    ApiResponse<List<ImageBranch>> findAll();

    ApiResponse<ImageBranch> findById(Long id);

    ApiResponse<ImageBranch> save(ImageBranch imageBranch);

    ApiResponse<ImageBranch> update(Long id, ImageBranch imageBranch);

    ApiResponse<String> deleteById(Long id);
}
