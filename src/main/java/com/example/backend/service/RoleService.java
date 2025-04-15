package com.example.backend.service;

import com.example.backend.entity.Role;
import com.example.backend.repository.RoleReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleReponsitory roleRepository;

    /**
     * Lấy tất cả các role.
     *
     * @return danh sách các role
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Lấy role theo ID.
     *
     * @param id ID của role cần tìm
     * @return Role nếu tồn tại, nếu không trả về Optional.empty()
     */
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    /**
     * Tạo mới hoặc cập nhật một role.
     *
     * @param role đối tượng role cần lưu
     * @return đối tượng role đã được lưu (tạo mới hoặc cập nhật)
     */
    public Role saveRole(Role role) {
        // Xử lý logic khi cần thiết trước khi lưu
        // Ví dụ: Nếu cần gán thời gian tạo và cập nhật

        // Cập nhật thời gian
        if (role.getCreated_at() == null) {
            role.setCreated_at(new Date(System.currentTimeMillis()));
        }
        role.setUpdated_at(new Date(System.currentTimeMillis()));

        return roleRepository.save(role);
    }

    /**
     * Xóa một role theo ID.
     *
     * @param id ID của role cần xóa
     */
    public void deleteRole(Long id) {
        // Kiểm tra xem role có tồn tại trước khi xóa
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Role không tồn tại với ID: " + id);
        }
    }


}
