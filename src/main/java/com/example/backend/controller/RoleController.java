package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Role;
import com.example.backend.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ApiResponse<List<Role>> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<Role> getRoleById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping
    public ApiResponse<Role> createRole(@Valid @RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public ApiResponse<Role> updateRole(@Valid @PathVariable Long id, @RequestBody Role role) {
        return roleService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.deleteById(id));
    }
}
