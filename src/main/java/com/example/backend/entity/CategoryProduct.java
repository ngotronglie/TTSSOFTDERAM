package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories_product")
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_category_product")
    @SequenceGenerator(name = "id_category_product", sequenceName = "CATEGORY_PRODUCT_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_categories_product;
    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "parent_category_product_id")
    private int parent_category_product_id;

    @Column(name = "is_active")
    private int is_active;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public int getParent_category_product_id() {
        return parent_category_product_id;
    }

    public void setParent_category_product_id(int parent_category_product_id) {
        this.parent_category_product_id = parent_category_product_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getId_categories_product() {
        return id_categories_product;
    }

    public void setId_categories_product(Long id_categories_product) {
        this.id_categories_product = id_categories_product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

}
