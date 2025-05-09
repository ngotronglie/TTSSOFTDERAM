package com.example.backend.dto;

import java.util.Map;

public class CategoryResponseDTO {
    private String image_category;
    private Map<String, CategoryChildDTO> category_parent_child;
    private Map<String, ImageBranchDTO> image_branch;
    private Map<String, ProductDTO> product;

    public CategoryResponseDTO(String image_category, 
                             Map<String, CategoryChildDTO> category_parent_child,
                             Map<String, ImageBranchDTO> image_branch,
                             Map<String, ProductDTO> product) {
        this.image_category = image_category;
        this.category_parent_child = category_parent_child;
        this.image_branch = image_branch;
        this.product = product;
    }

    public String getImage_category() {
        return image_category;
    }

    public void setImage_category(String image_category) {
        this.image_category = image_category;
    }

    public Map<String, CategoryChildDTO> getCategory_parent_child() {
        return category_parent_child;
    }

    public void setCategory_parent_child(Map<String, CategoryChildDTO> category_parent_child) {
        this.category_parent_child = category_parent_child;
    }

    public Map<String, ImageBranchDTO> getImage_branch() {
        return image_branch;
    }

    public void setImage_branch(Map<String, ImageBranchDTO> image_branch) {
        this.image_branch = image_branch;
    }

    public Map<String, ProductDTO> getProduct() {
        return product;
    }

    public void setProduct(Map<String, ProductDTO> product) {
        this.product = product;
    }
} 