package com.example.backend.controller;

import com.example.backend.entity.CommentNew;
import com.example.backend.service.CommentNewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/comment-new")
public class CommentNewController {

    private final CommentNewService commentNewService;

    public CommentNewController(CommentNewService commentNewService) {
        this.commentNewService = commentNewService;
    }

    // Lấy tất cả comment
    @GetMapping
    public List<CommentNew> getAllComments() {
        return commentNewService.findAll();
    }

    // Lấy comment theo ID
    @GetMapping("/{id}")
    public CommentNew getCommentById(@PathVariable Long id) {
        CommentNew comment = commentNewService.findById(id);
        if (comment == null) {
            throw new EntityNotFoundException("Comment with id " + id + " not found");
        }
        return comment;
    }

    // Tạo comment mới
    @PostMapping
    public CommentNew createComment(@RequestBody CommentNew comment) {
        comment.setCreated_at( new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comment.setUpdated_at( new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return commentNewService.save(comment);
    }

    // Cập nhật comment
    @PutMapping("/{id}")
    public CommentNew updateComment(@PathVariable Long id, @RequestBody CommentNew updatedComment) {
        CommentNew existing = commentNewService.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException("Comment news with id " + id + " not found");
        }
        existing.setMessage(updatedComment.getMessage());
        existing.setUser_id(updatedComment.getUser_id());
        existing.setNews_id(updatedComment.getNews_id());
        existing.setParent_comment_id(updatedComment.getParent_comment_id());
        existing.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return commentNewService.save(existing);
    }

    // Xóa comment
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentNewService.deleteById(id);
    }
}
