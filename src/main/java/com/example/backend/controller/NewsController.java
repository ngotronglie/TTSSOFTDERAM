package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.News;
import com.example.backend.service.NewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewService newService;

    public NewsController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping
    public ApiResponse<List<News>> getAllNews() {
        return newService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<News> getNewsById(@PathVariable Long id) {
        return newService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<News>> createNews(@RequestBody News news) {
        try {
            ApiResponse<News> response = newService.save(news);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi tạo tin tức", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<News>> updateNews(@PathVariable Long id, @RequestBody News news) {
        try {
            ApiResponse<News> response = newService.update(id, news);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi cập nhật tin tức", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNews(@PathVariable Long id) {
        try {
            ApiResponse<String> response = newService.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi xóa tin tức", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }
}
