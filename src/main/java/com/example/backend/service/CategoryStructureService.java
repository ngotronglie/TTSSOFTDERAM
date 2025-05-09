package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CategoryResponseDTO;
import java.util.Map;

public interface CategoryStructureService {
    ApiResponse<Map<String, CategoryResponseDTO>> getCategoryStructure();
} 