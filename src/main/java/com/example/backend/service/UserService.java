package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.UserTDO;
import com.example.backend.entity.User;

import java.util.List;

public interface UserService {
    ApiResponse<List<User>> findAll();

    ApiResponse<User> findById(Long id);

    ApiResponse<User> save(User user);

    ApiResponse<User> update(Long id, User user);

    ApiResponse<String> deleteById(Long id);

    // Thêm phương thức findByEmailAndPassword
    ApiResponse<UserTDO> findByEmailAndPassword(String email, String password);
}
