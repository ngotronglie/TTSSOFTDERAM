package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Coupons;

import java.util.List;

public interface CouponsService {
    ApiResponse<List<Coupons>> findAll();
    ApiResponse<Coupons> findById(Long id);
    ApiResponse<Coupons> save(Coupons coupons);
    ApiResponse<Coupons> update(Long id, Coupons coupons);
    ApiResponse<String> deleteById(Long id);
}
