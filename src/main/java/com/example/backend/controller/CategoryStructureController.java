package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CategoryResponseDTO;
import com.example.backend.service.CategoryStructureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/category-structure")
public class CategoryStructureController {

    private final CategoryStructureService categoryStructureService;

    public CategoryStructureController(CategoryStructureService categoryStructureService) {
        this.categoryStructureService = categoryStructureService;
    }

    @GetMapping
    public ApiResponse<Map<String, CategoryResponseDTO>> getCategoryStructure() {
        return categoryStructureService.getCategoryStructure();
    }
} 