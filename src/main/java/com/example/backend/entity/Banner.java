package com.example.backend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "banners")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_banner")
    @SequenceGenerator(name = "id_banner", sequenceName = "BANNER_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_banner;

    @Column(name = "image_url_banner", nullable = false)
    private String image_url_banner;



    @Column(name = "is_status", nullable = false)
    private int is_status;

    @Column(name = "category_id", nullable = false)
    private int category_id;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Long getId_banner() {
        return id_banner;
    }

    public void setId_banner(Long id_banner) {
        this.id_banner = id_banner;
    }

    public String getImage_url_banner() {
        return image_url_banner;
    }

    public void setImage_url_banner(String image_url_banner) {
        this.image_url_banner = image_url_banner;
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
    public int getIs_status() {
        return is_status;
    }

    public void setIs_status(int is_status) {
        this.is_status = is_status;
    }

    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
