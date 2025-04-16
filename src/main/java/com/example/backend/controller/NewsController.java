package com.example.backend.controller;

import com.example.backend.entity.News;
import com.example.backend.service.NewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewService newService;

    public NewsController(NewService newService) {
        this.newService = newService;
    }

    // ✅ Lấy tất cả News
    @GetMapping
    public List<News> getAllNews() {
        return newService.findAll();
    }

    // ✅ Lấy News theo ID
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) {
        News news = newService.findById(id);
        if (news == null) {
            throw new EntityNotFoundException("News with id " + id + " not found");
        }
        return news;
    }

    // ✅ Tạo mới một bản ghi News
    @PostMapping
    public News createNews(@RequestBody News news) {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        news.setCreated_at(now);
        news.setUpdated_at(now);
        return newService.save(news);
    }

    // ✅ Cập nhật thông tin News
    @PutMapping("/{id}")
    public News updateNews(@PathVariable Long id, @RequestBody News news) {
        News existing = newService.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException("News with id " + id + " not found");
        }

        existing.setTitle(news.getTitle());
        existing.setHtml(news.getHtml());
        existing.setUser_post_id(news.getUser_post_id());
        existing.setCategory_news_id(news.getCategory_news_id());
        existing.setUpdated_at(LocalDateTime.now(ZoneId.systemDefault()));

        return newService.save(existing);
    }

    // ✅ Xoá News theo ID
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newService.deleteById(id);
    }
}
