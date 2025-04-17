package com.example.backend.controller;


import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Lấy tất cả các Role
    @GetMapping
    public List<User> getAllRoles() {
        return userService.findAll(); // Gọi phương thức findAll trong RoleService
    }

    // Lấy Role theo ID
    @GetMapping("/{id}")
    public User getRoleById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Tạo mới một Role
    @PostMapping
    public User createRole(@RequestBody User user) {
        user.setLast_login_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        user.setCreated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        user.setUpdated_at(user.getCreated_at());
        return userService.save(user);
    }

    // Cập nhật thông tin của một Role
    @PutMapping("/{id}")
    public User updateRole(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.findById(id);

        if (existingUser == null) {
            throw new EntityNotFoundException("Role with id " + id + " not found");
        }

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole_id(user.getRole_id());
        existingUser.setStatus(user.getStatus());
        existingUser.setEmail_verified_at(user.getEmail_verified_at());
        existingUser.setProvider(user.getProvider());
        existingUser.setProvider_id(user.getProvider_id());
        existingUser.setCreated_at(user.getCreated_at());
        existingUser.setUpdated_at(user.getCreated_at());
        return userService.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
