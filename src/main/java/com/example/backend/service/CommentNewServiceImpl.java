package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CommentNew;
import com.example.backend.repository.CommentNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentNewServiceImpl implements CommentNewService {

    private final CommentNewRepository commentNewRepository;

    @Autowired
    public CommentNewServiceImpl(CommentNewRepository commentNewRepository) {
        this.commentNewRepository = commentNewRepository;
    }

    @Override
    public ApiResponse<List<CommentNew>> findAll() {
        List<CommentNew> commentNews = commentNewRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách bình luận thành công", LocalDateTime.now(), commentNews, null);
    }

    @Override
    public ApiResponse<CommentNew> findById(Long id) {
        CommentNew commentNew = commentNewRepository.findById(id).orElse(null);

        if (commentNew == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy bình luận với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy bình luận", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Bình luận được tìm thấy", LocalDateTime.now(), commentNew, null);
    }

    @Override
    public ApiResponse<CommentNew> save(CommentNew commentNew) {
        commentNew.setCreated_at(LocalDate.now());
        commentNew.setUpdated_at(LocalDate.now());
        CommentNew savedCommentNew = commentNewRepository.save(commentNew);
        return new ApiResponse<>("success", "Tạo bình luận thành công", LocalDateTime.now(), savedCommentNew, null);
    }

    @Override
    public ApiResponse<CommentNew> update(Long id, CommentNew commentNew) {
        CommentNew existingCommentNew = commentNewRepository.findById(id).orElse(null);

        if (existingCommentNew == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy bình luận với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy bình luận", LocalDateTime.now(), null, errorMessages);
        }

        existingCommentNew.setMessage(commentNew.getMessage());
        existingCommentNew.setUpdated_at(LocalDate.now());
        existingCommentNew.setUser_id(commentNew.getUser_id());
        existingCommentNew.setNews_id(commentNew.getNews_id());
        existingCommentNew.setParent_comment_id(commentNew.getParent_comment_id());
        CommentNew updatedCommentNew = commentNewRepository.save(existingCommentNew);

        return new ApiResponse<>("success", "Cập nhật bình luận thành công", LocalDateTime.now(), updatedCommentNew, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        CommentNew existingCommentNew = commentNewRepository.findById(id).orElse(null);

        if (existingCommentNew == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy bình luận với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy bình luận", LocalDateTime.now(), null, errorMessages);
        }
        commentNewRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa bình luận thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
