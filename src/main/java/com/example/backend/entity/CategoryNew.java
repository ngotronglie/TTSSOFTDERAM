package com.example.backend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "category_news")
public class CategoryNew {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_category_new")
    @SequenceGenerator(name = "id_category_new", sequenceName = "CATEGORY_NEW_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_category_new;

    @Column(name = "name_category_new", nullable = false)
    private String name_category_new;

    @Column(name = "parent_category_new_id", nullable = false)
    private int parent_category_new_id;

    @Column(name = "image_category_new", nullable = false)
    private String image_category_new;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;


    @Column(name = "is_active", nullable = false)
    private int is_active;

    public Long getId_category_new() {
        return id_category_new;
    }

    public void setId_category_new(Long id_category_new) {
        this.id_category_new = id_category_new;
    }


    public String getName_category_new() {
        return name_category_new;
    }

    public void setName_category_new(String name_category_new) {
        this.name_category_new = name_category_new;
    }

    public int getParent_category_new_id() {
        return parent_category_new_id;
    }

    public void setParent_category_new_id(int parent_category_new_id) {
        this.parent_category_new_id = parent_category_new_id;
    }

    public String getImage_category_new() {
        return image_category_new;
    }

    public void setImage_category_new(String image_category_new) {
        this.image_category_new = image_category_new;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }




}
