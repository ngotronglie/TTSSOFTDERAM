package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.News;
import com.example.backend.repository.NewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    public NewServiceImpl(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    @Override
    public ApiResponse<List<News>> findAll() {
        List<News> newsList = newRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách tin tức thành công", LocalDateTime.now(), newsList, null);
    }

    @Override
    public ApiResponse<News> findById(Long id) {
        News news = newRepository.findById(id).orElse(null);
        if (news == null) {
            return new ApiResponse<>("error", "Không tìm thấy tin tức", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }
        return new ApiResponse<>("success", "Tìm tin tức thành công", LocalDateTime.now(), news, null);
    }

    @Override
    public ApiResponse<News> save(News news) {
        news.setCreated_at(LocalDateTime.now());
        news.setUpdated_at(LocalDateTime.now());
        News saved = newRepository.save(news);
        return new ApiResponse<>("success", "Thêm tin tức thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<News> update(Long id, News updatedNews) {
        News existing = newRepository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy tin tức", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }

        existing.setTitle(updatedNews.getTitle());
        existing.setHtml(updatedNews.getHtml());
        existing.setCategory_news_id(updatedNews.getCategory_news_id());
        existing.setUser_post_id(updatedNews.getUser_post_id());
        existing.setUpdated_at(LocalDateTime.now());

        News saved = newRepository.save(existing);
        return new ApiResponse<>("success", "Cập nhật tin tức thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        News news = newRepository.findById(id).orElse(null);
        if (news == null) {
            return new ApiResponse<>("error", "Không tìm thấy tin tức", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }

        newRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa tin tức thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
