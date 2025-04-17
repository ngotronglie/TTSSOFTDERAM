package com.example.backend.controller;

import com.example.backend.entity.Role;
import com.example.backend.service.RoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleServiceImpl roleServiceImpl;

    public RoleController(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    // Lấy tất cả các Role
    @GetMapping
    public List<Role> getAllRoles() {
        return roleServiceImpl.findAll(); // Gọi phương thức findAll trong RoleService
    }

    // Lấy Role theo ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleServiceImpl.findById(id); // Gọi phương thức findById trong RoleService
    }

    // Tạo mới một Role
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleServiceImpl.save(role); // Gọi phương thức save trong RoleService
    }

    // Cập nhật thông tin của một Role
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        Role existingRole = roleServiceImpl.findById(id);

        if (existingRole == null) {
            throw new EntityNotFoundException("Role with id " + id + " not found");
        }
        existingRole.setName_role(role.getName_role());
        existingRole.setComment(role.getComment());
        existingRole.setCreated_at(role.getCreated_at());
        existingRole.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return roleServiceImpl.save(existingRole);
    }

    // Xóa một Role theo ID
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleServiceImpl.deleteById(id); // Gọi phương thức deleteById trong RoleService
    }
}

