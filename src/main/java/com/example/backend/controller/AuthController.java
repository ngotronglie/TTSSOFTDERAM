package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.AuthTDO;
import com.example.backend.dto.LoginRequest;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090", "http://localhost:4200"}, allowCredentials = "true")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthTDO>> login(@RequestBody LoginRequest loginRequest) {
        try {
            ApiResponse<AuthTDO> response = userService.authenticate(loginRequest);
            if ("error".equals(response.getStatus())) {
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                new ApiResponse<>(
                    "error",
                    "Lỗi server khi xử lý đăng nhập",
                    null,
                    null,
                    List.of(e.getMessage())
                )
            );
        }
    }
}
