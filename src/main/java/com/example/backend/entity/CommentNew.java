package com.example.backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

@Entity
@Table(name = "comment_news")
public class CommentNew {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_comment_news")
    @SequenceGenerator(name = "id_comment_news", sequenceName = "COMMENT_NEW_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_comment_news;

    @Column(name = "message")
    @NotEmpty(message = "message khong duoc de trong!")
    private String message;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "news_id")
    private Long news_id;

    @Column(name = "parent_comment_id")
    private Long parent_comment_id;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "updated_at")
    private LocalDate updated_at;

    public Long getId_comment_news() {
        return id_comment_news;
    }

    public void setId_comment_news(Long id_comment_news) {
        this.id_comment_news = id_comment_news;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getNews_id() {
        return news_id;
    }

    public void setNews_id(Long news_id) {
        this.news_id = news_id;
    }

    public Long getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(Long parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
