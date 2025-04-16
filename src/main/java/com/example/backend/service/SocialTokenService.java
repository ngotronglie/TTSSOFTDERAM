package com.example.backend.service;

import com.example.backend.entity.Role;
import com.example.backend.entity.SocialToken;
import com.example.backend.repository.SocialTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SocialTokenService {
    private final SocialTokenRepository socialTokenRepository;

    public SocialTokenService(SocialTokenRepository socialTokenRepository) {
        this.socialTokenRepository = socialTokenRepository;
    }
    public List<SocialToken> findAll() {
        return socialTokenRepository.findAll();
    }

    public SocialToken findById(Long id) {
        return socialTokenRepository.findById(id).orElse(null);
    }

    public SocialToken save(SocialToken socialToken) {
        return socialTokenRepository.save(socialToken);
    }
    public void deleteById(Long id) {
        socialTokenRepository.deleteById(id);
    }
}
