package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.AuthTDO;
import com.example.backend.dto.LoginRequest;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthTDO> login(@RequestBody LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }
}
