package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.SocialToken;
import com.example.backend.service.SocialTokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090"}) // Hạn chế CORS cho các origin cụ thể

@RestController
@RequestMapping("/api/social-token")
public class SocialTokenController {

    private final SocialTokenService socialTokenService;

    public SocialTokenController(SocialTokenService socialTokenService) {
        this.socialTokenService = socialTokenService;
    }

    @GetMapping
    public ApiResponse<List<SocialToken>> getAll() {
        return socialTokenService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<SocialToken> getById(@PathVariable Long id) {
        return socialTokenService.findById(id);
    }

    @PostMapping
    public ApiResponse<SocialToken> create(@RequestBody SocialToken token) {
        return socialTokenService.save(token);
    }

    @PutMapping("/{id}")
    public ApiResponse<SocialToken> update(@PathVariable Long id, @RequestBody SocialToken token) {
        return socialTokenService.update(id, token);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        return socialTokenService.deleteById(id);
    }
}
