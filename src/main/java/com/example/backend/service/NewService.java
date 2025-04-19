package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.News;

import java.util.List;

public interface NewService {
    ApiResponse<List<News>> findAll();
    ApiResponse<News> findById(Long id);
    ApiResponse<News> save(News news);
    ApiResponse<News> update(Long id, News news);
    ApiResponse<String> deleteById(Long id);
}
