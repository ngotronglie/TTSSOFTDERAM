package com.example.backend.service;

import com.example.backend.entity.News;
import com.example.backend.repository.NewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewServiceImpl {
    private final NewRepository newRepository;

    public NewServiceImpl(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    public List<News> findAll() {
        return newRepository.findAll();
    }

    public News findById(Long id) {
        return newRepository.findById(id).orElse(null);
    }

    public News save(News news) {
        return newRepository.save(news);
    }

    public void deleteById(Long id) {
        newRepository.deleteById(id);
    }
}
