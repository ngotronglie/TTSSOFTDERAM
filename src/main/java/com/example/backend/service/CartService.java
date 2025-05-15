package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CartRequestDTO;
import com.example.backend.dto.CartDetailDTO;
import com.example.backend.entity.Cart;
import com.example.backend.entity.City;
import com.example.backend.repository.CartRepository;

import java.util.List;

public interface CartService {
    ApiResponse<List<Cart>> findAll();

    ApiResponse<Cart> findById(Long id);

    ApiResponse<Cart> save(Cart cart);

    ApiResponse<Cart> update(Long id, Cart cart);

    ApiResponse<String> deleteById(Long id);

    ApiResponse<Cart> addToCart(CartRequestDTO cartRequest);

    ApiResponse<Cart> updateCartItem(CartRequestDTO cartRequest);

    ApiResponse<String> removeFromCart(Integer userId, Integer productId);

    ApiResponse<List<CartDetailDTO>> getUserCart(Integer userId);

    ApiResponse<String> clearUserCart(Integer userId);
}
