package com.example.backend.service;
import com.example.backend.entity.Banner;
import com.example.backend.repository.BannerProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private final BannerProductRepository bannerProductRepository;

    public BannerService(BannerProductRepository bannerProductRepository) {
        this.bannerProductRepository = bannerProductRepository;
    }

    public List<Banner> findAll() {
        return bannerProductRepository.findAll();
    }

    public Banner findById(Long id) {
        return bannerProductRepository.findById(id).orElse(null);
    }

    public Banner save(Banner banner) {
        return bannerProductRepository.save(banner);
    }

    public void deleteById(Long id) {
        bannerProductRepository.deleteById(id);
    }
}
