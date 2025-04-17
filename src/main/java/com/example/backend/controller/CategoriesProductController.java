package com.example.backend.controller;

import com.example.backend.entity.CategoryProduct;
import com.example.backend.service.CategoriesProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/categories-products")
public class CategoriesProductController {

    private final CategoriesProductServiceImpl categoriesProductServiceImpl;

    public CategoriesProductController(CategoriesProductServiceImpl categoriesProductServiceImpl) {
        this.categoriesProductServiceImpl = categoriesProductServiceImpl;
    }

    @GetMapping
    public List<CategoryProduct> getAllCategoriesProducts() {
        return categoriesProductServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public CategoryProduct getCategoriesProductById(@PathVariable Long id) {
        return categoriesProductServiceImpl.findById(id);
    }

    @PostMapping
    public CategoryProduct createCategoriesProduct(@RequestBody CategoryProduct categoriesProduct) {
        return categoriesProductServiceImpl.save(categoriesProduct);
    }

    @PutMapping("/{id}")
    public CategoryProduct updateCategoriesProduct(@PathVariable Long id, @RequestBody CategoryProduct categoriesProduct) {
        CategoryProduct existingCategoryProduct = categoriesProductServiceImpl.findById(id);
        if (existingCategoryProduct == null) {
            throw new EntityNotFoundException("CategoriesProduct with id " + id + " not found");
        }

        existingCategoryProduct.setName(categoriesProduct.getName()); // Cập nhật tên, hoặc thuộc tính khác
        existingCategoryProduct.setImage(categoriesProduct.getImage());
        existingCategoryProduct.setParent_category_product_id(categoriesProduct.getParent_category_product_id());
        existingCategoryProduct.setIs_active(categoriesProduct.getIs_active());
        existingCategoryProduct.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        return categoriesProductServiceImpl.save(existingCategoryProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoriesProduct(@PathVariable Long id) {
        categoriesProductServiceImpl.deleteById(id);
    }
}
