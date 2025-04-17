package com.example.backend.controller;

import com.example.backend.entity.CategoryNew;
import com.example.backend.service.CategoryNewServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/categories-news")
public class CategoryNewController {

    private final CategoryNewServiceImpl categoryNewServiceImpl;

    // Constructor để inject CategoryNewService
    public CategoryNewController(CategoryNewServiceImpl categoryNewServiceImpl) {
        this.categoryNewServiceImpl = categoryNewServiceImpl;
    }

    // Lấy tất cả các CategoryNew
    @GetMapping
    public List<CategoryNew> getAllCategoriesNew() {
        return categoryNewServiceImpl.findAll();
    }

    // Lấy CategoryNew theo ID
    @GetMapping("/{id}")
    public CategoryNew getCategoriesNewById(@PathVariable Long id) {
        return categoryNewServiceImpl.findById(id);
    }

    // Tạo mới CategoryNew
    @PostMapping
    public CategoryNew createCategoriesNew(@RequestBody CategoryNew categoryNew) {
        return categoryNewServiceImpl.save(categoryNew);
    }

    // Cập nhật CategoryNew theo ID
    @PutMapping("/{id}")
    public CategoryNew updateCategoriesNew(@PathVariable Long id, @RequestBody CategoryNew categoryNew) {
        // Lấy CategoryNew hiện tại từ service
        CategoryNew existingCategoryNew = categoryNewServiceImpl.findById(id);
        if (existingCategoryNew == null) {
            throw new EntityNotFoundException("CategoriesNews with id " + id + " not found");
        }
        existingCategoryNew.setName_category_new(categoryNew.getName_category_new());
        existingCategoryNew.setParent_category_new_id(categoryNew.getParent_category_new_id());
        existingCategoryNew.setImage_category_new(categoryNew.getImage_category_new());
        existingCategoryNew.setCreated_at(categoryNew.getCreated_at());
        existingCategoryNew.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        existingCategoryNew.setIs_active(categoryNew.getIs_active());
        return categoryNewServiceImpl.save(existingCategoryNew);
    }
    // Xóa CategoryNew theo ID
    @DeleteMapping("/{id}")
    public void deleteCategoriesNew(@PathVariable Long id) {
        categoryNewServiceImpl.deleteById(id);
    }
}