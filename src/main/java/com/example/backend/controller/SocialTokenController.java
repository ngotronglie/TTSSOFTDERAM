package com.example.backend.controller;

import com.example.backend.entity.SocialToken;
import com.example.backend.service.SocialTokenServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/social-token")
public class SocialTokenController {

    private final SocialTokenServiceImpl socialTokenServiceImpl;

    public SocialTokenController(SocialTokenServiceImpl socialTokenServiceImpl) {
        this.socialTokenServiceImpl = socialTokenServiceImpl;
    }

    // Lấy tất cả các SocialToken
    @GetMapping
    public List<SocialToken> getAllSocialTokens() {
        return socialTokenServiceImpl.findAll();
    }

    // Lấy SocialToken theo ID
    @GetMapping("/{id}")
    public SocialToken getSocialTokenById(@PathVariable Long id) {
        return socialTokenServiceImpl.findById(id);
    }

    // Tạo mới một SocialToken
    @PostMapping
    public SocialToken createSocialToken(@RequestBody SocialToken socialToken) {
        return socialTokenServiceImpl.save(socialToken);
    }

    // Cập nhật thông tin của một SocialToken
    @PutMapping("/{id}")
    public SocialToken updateSocialToken(@PathVariable Long id, @RequestBody SocialToken socialToken) {
        SocialToken existingSocialToken = socialTokenServiceImpl.findById(id);

        if (existingSocialToken == null) {
            throw new EntityNotFoundException("SocialToken with id " + id + " not found");
        }

        existingSocialToken.setUser_id(socialToken.getUser_id());
        existingSocialToken.setProvider(socialToken.getProvider());
        existingSocialToken.setAccess_token(socialToken.getAccess_token());
        existingSocialToken.setRefresh_token(socialToken.getRefresh_token());
        existingSocialToken.setExpires_in(socialToken.getExpires_in());
        existingSocialToken.setToken_type(socialToken.getToken_type());
        existingSocialToken.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        return socialTokenServiceImpl.save(existingSocialToken);
    }

    // Xóa một SocialToken theo ID
    @DeleteMapping("/{id}")
    public void deleteSocialToken(@PathVariable Long id) {
        socialTokenServiceImpl.deleteById(id);
    }
}
