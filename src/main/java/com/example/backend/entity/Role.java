package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_role")
    @SequenceGenerator(name = "id_role", sequenceName = "ROLE_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_role;


    @Column(name = "name_role", nullable = false)
    private String name_role;

    @Column(name = "comment_role")
    private String comment_role;
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;


    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public String getName_role() {
        return name_role;
    }

    public void setName_role(String name_role) {
        this.name_role = name_role;
    }

    public String getComment() {
        return comment_role;
    }

    public void setComment(String comment_role) {
        this.comment_role = comment_role;
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
