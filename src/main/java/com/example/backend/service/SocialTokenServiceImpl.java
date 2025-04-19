package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.SocialToken;
import com.example.backend.repository.SocialTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocialTokenServiceImpl implements SocialTokenService {

    private final SocialTokenRepository repository;

    public SocialTokenServiceImpl(SocialTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApiResponse<List<SocialToken>> findAll() {
        return new ApiResponse<>("success", "Lấy danh sách token thành công", LocalDateTime.now(), repository.findAll(), null);
    }

    @Override
    public ApiResponse<SocialToken> findById(Long id) {
        SocialToken token = repository.findById(id).orElse(null);
        if (token == null) {
            return new ApiResponse<>("error", "Không tìm thấy token", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }
        return new ApiResponse<>("success", "Lấy token thành công", LocalDateTime.now(), token, null);
    }

    @Override
    public ApiResponse<SocialToken> save(SocialToken token) {
        token.setCreated_at(LocalDateTime.now());
        token.setUpdated_at(LocalDateTime.now());
        return new ApiResponse<>("success", "Lưu token thành công", LocalDateTime.now(), repository.save(token), null);
    }

    @Override
    public ApiResponse<SocialToken> update(Long id, SocialToken updatedToken) {
        SocialToken existing = repository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy token", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }

        existing.setUser_id(updatedToken.getUser_id());
        existing.setAccess_token(updatedToken.getAccess_token());
        existing.setRefresh_token(updatedToken.getRefresh_token());
        existing.setProvider(updatedToken.getProvider());
        existing.setToken_type(updatedToken.getToken_type());
        existing.setExpires_in(updatedToken.getExpires_in());
        existing.setUpdated_at(LocalDateTime.now());

        return new ApiResponse<>("success", "Cập nhật token thành công", LocalDateTime.now(), repository.save(existing), null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        SocialToken existing = repository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy token", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }

        repository.deleteById(id);
        return new ApiResponse<>("success", "Xóa token thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
