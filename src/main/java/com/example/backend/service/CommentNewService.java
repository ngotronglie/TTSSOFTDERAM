package com.example.backend.service;

import com.example.backend.entity.CommentNew;
import com.example.backend.repository.CommentNewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentNewService {

    private final CommentNewRepository commentNewRepository;

    public CommentNewService(CommentNewRepository commentNewRepository ) {
        this.commentNewRepository = commentNewRepository;
    }

    public List<CommentNew> findAll() {
        return commentNewRepository.findAll();
    }

    public CommentNew findById(Long id) {
        return commentNewRepository.findById(id).orElse(null);
    }

    public CommentNew save(CommentNew commentNew) {
        return commentNewRepository.save(commentNew);
    }

    public void deleteById(Long id) {
        commentNewRepository.deleteById(id);
    }
}
