package com.example.backend.repository;

import com.example.backend.entity.SocialToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialTokenRepository extends JpaRepository<SocialToken, Long> {
}
