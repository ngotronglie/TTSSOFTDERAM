package com.example.backend.service;

import com.example.backend.entity.CategoryProduct;
import com.example.backend.repository.CategoriesProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriesProductServiceImpl {

    private final CategoriesProductRepository categoriesProductRepository;

    public CategoriesProductServiceImpl(CategoriesProductRepository categoriesProductRepository) {
        this.categoriesProductRepository = categoriesProductRepository;
    }

    public List<CategoryProduct> findAll() {
        return categoriesProductRepository.findAll();
    }

    public CategoryProduct findById(Long id) {
        return categoriesProductRepository.findById(id).orElse(null);
    }

    public CategoryProduct save(CategoryProduct categoryProduct) {
        return categoriesProductRepository.save(categoryProduct);
    }

    public void deleteById(Long id) {
        categoriesProductRepository.deleteById(id);
    }
}
