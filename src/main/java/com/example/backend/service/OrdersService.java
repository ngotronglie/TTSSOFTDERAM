package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.OrderRequest;
import com.example.backend.dto.UserOrderResponseDTO;
import com.example.backend.entity.Orders;

import java.util.List;

public interface OrdersService {
    ApiResponse<List<Orders>> findAll();
    ApiResponse<Orders> findById(Long id);
    ApiResponse<Orders> save(Orders orders);
    ApiResponse<Orders> update(Long id, Orders orders);
    ApiResponse<String> deleteById(Long id);
    ApiResponse<Orders> saveOrder(OrderRequest orderRequest);
    ApiResponse<List<UserOrderResponseDTO>> getUserOrders(int userId);
    ApiResponse<UserOrderResponseDTO> getOrderByCode(String code);
}
