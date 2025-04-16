package com.example.backend.controller;

import com.example.backend.entity.Role;
import com.example.backend.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Lấy tất cả các Role
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findAll(); // Gọi phương thức findAll trong RoleService
    }

    // Lấy Role theo ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.findById(id); // Gọi phương thức findById trong RoleService
    }

    // Tạo mới một Role
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role); // Gọi phương thức save trong RoleService
    }

    // Cập nhật thông tin của một Role
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        Role existingRole = roleService.findById(id);

        if (existingRole == null) {
            throw new EntityNotFoundException("Role with id " + id + " not found");
        }
        existingRole.setName_role(role.getName_role());
        existingRole.setComment(role.getComment());
        existingRole.setCreated_at(role.getCreated_at());
        existingRole.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return roleService.save(existingRole);
    }

    // Xóa một Role theo ID
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteById(id); // Gọi phương thức deleteById trong RoleService
    }
}
