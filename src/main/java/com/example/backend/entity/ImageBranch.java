package com.example.backend.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "image_branch")
public class ImageBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_image_branch")
    @SequenceGenerator(name = "id_image_branch", sequenceName = "IMAGE_BRANCH_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_image_branch;

    @Column(name = "image_branch")
    private String image_branch;

    @Column(name = "is_status")
    private String is_status;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Long getId_image_branch() {
        return id_image_branch;
    }

    public void setId_image_branch(Long id_image_branch) {
        this.id_image_branch = id_image_branch;
    }

    public String getImage_branch() {
        return image_branch;
    }

    public void setImage_branch(String image_branch) {
        this.image_branch = image_branch;
    }

    public String getIs_status() {
        return is_status;
    }

    public void setIs_status(String is_status) {
        this.is_status = is_status;
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
