package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CommentNew;

import java.util.List;

public interface CommentNewService {

    ApiResponse<List<CommentNew>> findAll();

    ApiResponse<CommentNew> findById(Long id);

    ApiResponse<CommentNew> save(CommentNew commentNew);

    ApiResponse<CommentNew> update(Long id, CommentNew commentNew);

    ApiResponse<String> deleteById(Long id);
}
