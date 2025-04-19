package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Role;
import com.example.backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ApiResponse<List<Role>> findAll() {
        List<Role> roles = roleRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách vai trò thành công", LocalDateTime.now(), roles, null);
    }

    @Override
    public ApiResponse<Role> findById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            return new ApiResponse<>("error", "Không tìm thấy vai trò", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }
        return new ApiResponse<>("success", "Tìm vai trò thành công", LocalDateTime.now(), role, null);
    }

    @Override
    public ApiResponse<Role> save(Role role) {
        role.setCreated_at(LocalDateTime.now());
        role.setUpdated_at(LocalDateTime.now());
        Role saved = roleRepository.save(role);
        return new ApiResponse<>("success", "Thêm vai trò thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<Role> update(Long id, Role updatedRole) {
        Role existing = roleRepository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy vai trò", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }

        existing.setName_role(updatedRole.getName_role());
        existing.setComment(updatedRole.getComment());
        existing.setUpdated_at(LocalDateTime.now());

        Role saved = roleRepository.save(existing);
        return new ApiResponse<>("success", "Cập nhật vai trò thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            return new ApiResponse<>("error", "Không tìm thấy vai trò", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }

        roleRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa vai trò thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
