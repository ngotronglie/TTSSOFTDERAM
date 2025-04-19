package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Role;

import java.util.List;

public interface RoleService {
    ApiResponse<List<Role>> findAll();

    ApiResponse<Role> findById(Long id);

    ApiResponse<Role> save(Role role);

    ApiResponse<Role> update(Long id, Role role);

    ApiResponse<String> deleteById(Long id);
}
