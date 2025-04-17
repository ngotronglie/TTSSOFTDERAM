package com.example.backend.repository;

import com.example.backend.entity.CommentNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentNewRepository extends JpaRepository<CommentNew, Long> {
}
