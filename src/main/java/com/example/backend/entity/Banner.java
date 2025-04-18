package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_banner")
    @SequenceGenerator(name = "id_banner", sequenceName = "BANNER_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_banner;

    @Column(name = "image_url_banner")
    private String image_url_banner;

    @Column(name = "is_status")
    @Min(value = 0, message = "Trạng thái không được để trống hoặc nhỏ hơn 0")
    private int is_status;

    @Column(name = "category_id")
    @Min(value = 0, message = "Category ID không được để trống hoặc nhỏ hơn 1")
    private int category_id;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    // Getters và Setters
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
}
