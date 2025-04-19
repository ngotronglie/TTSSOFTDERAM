package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CommentNew;
import com.example.backend.service.CommentNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment-new")
public class CommentNewController {

    private final CommentNewService commentNewService;

    @Autowired
    public CommentNewController(CommentNewService commentNewService) {
        this.commentNewService = commentNewService;
    }

    @GetMapping
    public ApiResponse<List<CommentNew>> getAllComments() {
        return commentNewService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<CommentNew> getCommentById(@PathVariable Long id) {
        return commentNewService.findById(id);
    }

    @PostMapping
    public ApiResponse<CommentNew> createComment(@RequestBody CommentNew commentNew) {
        return commentNewService.save(commentNew);
    }

    @PutMapping("/{id}")
    public ApiResponse<CommentNew> updateComment(@PathVariable Long id, @RequestBody CommentNew commentNew) {
        return commentNewService.update(id, commentNew);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteComment(@PathVariable Long id) {
        return commentNewService.deleteById(id);
    }
}
