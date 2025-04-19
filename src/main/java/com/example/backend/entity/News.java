package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_new")
    @SequenceGenerator(name = "id_new", sequenceName = "NEWS_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_new;

    @Column(name = "category_news_id")
    private int category_news_id;

    @Column(name = "title")
    private String title;

    @Column(name = "html")
    @Lob
    private String html;

    @Column(name = "user_post_id")
    private int user_post_id;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public int getCategory_news_id() {
        return category_news_id;
    }

    public void setCategory_news_id(int category_news_id) {
        this.category_news_id = category_news_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Long getId_new() {
        return id_new;
    }

    public void setId_new(Long id_new) {
        this.id_new = id_new;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_post_id() {
        return user_post_id;
    }

    public void setUser_post_id(int user_post_id) {
        this.user_post_id = user_post_id;
    }
}
