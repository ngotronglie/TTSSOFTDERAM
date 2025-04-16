package com.example.backend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "image_varian")
public class ImageVarian {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_image_varian")
    @SequenceGenerator(name = "id_image_varian", sequenceName = "IMAGE_VARIAN_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_image_varian;

    @Column(name = "image_url", nullable = false)
    private String image_url;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Long getId_image_varian() {
        return id_image_varian;
    }

    public void setId_image_varian(Long id_image_varian) {
        this.id_image_varian = id_image_varian;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
