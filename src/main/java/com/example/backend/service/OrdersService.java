package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Orders;

import java.util.List;

public interface OrdersService {
    ApiResponse<List<Orders>> findAll();
    ApiResponse<Orders> findById(Long id);
    ApiResponse<Orders> save(Orders orders);
    ApiResponse<Orders> update(Long id, Orders orders);
    ApiResponse<String> deleteById(Long id);
}
