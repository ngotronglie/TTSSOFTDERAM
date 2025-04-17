package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Banner;

import java.util.List;

public interface BannerService {
    ApiResponse<List<Banner>> findAll();

    ApiResponse<Banner> findById(Long id);

    ApiResponse<Banner> save(Banner banner);

    ApiResponse<Banner> update(Long id, Banner banner);

    ApiResponse<String> deleteById(Long id);
}
