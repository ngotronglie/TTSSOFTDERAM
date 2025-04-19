package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.SocialToken;

import java.util.List;

public interface SocialTokenService {
    ApiResponse<List<SocialToken>> findAll();

    ApiResponse<SocialToken> findById(Long id);

    ApiResponse<SocialToken> save(SocialToken token);

    ApiResponse<SocialToken> update(Long id, SocialToken token);

    ApiResponse<String> deleteById(Long id);
}
