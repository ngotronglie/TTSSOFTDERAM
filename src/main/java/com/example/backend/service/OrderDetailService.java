package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    ApiResponse<List<OrderDetail>> findAll();

    ApiResponse<OrderDetail> findById(Long id);

    ApiResponse<OrderDetail> save(OrderDetail orderDetail);

    ApiResponse<OrderDetail> update(Long id, OrderDetail orderDetail);

    ApiResponse<String> deleteById(Long id);
}
