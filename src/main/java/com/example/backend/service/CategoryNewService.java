package com.example.backend.service;

import com.example.backend.entity.CategoryNew;
import com.example.backend.repository.CategoryNewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryNewService {

    private final CategoryNewRepository categoryNewRepository;

    // Constructor
    public CategoryNewService(CategoryNewRepository categoryNewRepository) {
        this.categoryNewRepository = categoryNewRepository;
    }

    // Lấy tất cả các CategoryNew
    public List<CategoryNew> findAll() {
        return categoryNewRepository.findAll();
    }

    // Lấy CategoryNew theo ID
    public CategoryNew findById(Long id) {
        return categoryNewRepository.findById(id).orElse(null);
    }

    // Lưu CategoryNew
    public CategoryNew save(CategoryNew categoryNew) {
        return categoryNewRepository.save(categoryNew);
    }

    // Xóa CategoryNew theo ID
    public void deleteById(Long id) {
        categoryNewRepository.deleteById(id);
    }
}
