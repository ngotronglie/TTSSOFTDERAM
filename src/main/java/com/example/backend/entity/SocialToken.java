package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "social_tokens")
public class SocialToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_social_tokens")
    @SequenceGenerator(name = "id_social_tokens", sequenceName = "SOCIAL_TOKEN_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_social_tokens;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "access_token")
    @Lob
    private String access_token;

    @Column(name = "refresh_token")
    @Lob
    private String refresh_token;

    @Column(name = "expires_in")
    private int expires_in;

    @Column(name = "token_type")
    private String token_type;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public Long getId_social_tokens() {
        return id_social_tokens;
    }
    public void setId_social_tokens(Long id_social_tokens) {
        this.id_social_tokens = id_social_tokens;
    }
    public String getRefresh_token() {
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public LocalDateTime getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}