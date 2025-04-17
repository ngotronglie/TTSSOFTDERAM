package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Banner;
import com.example.backend.repository.BannerProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    private final BannerProductRepository bannerRepository;

    public BannerServiceImpl(BannerProductRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public ApiResponse<List<Banner>> findAll() {
        List<Banner> banners = bannerRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách banner thành công", LocalDateTime.now(), banners, null);
    }

    @Override
    public ApiResponse<Banner> findById(Long id) {
        Banner banner = bannerRepository.findById(id).orElse(null);
        if (banner == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy banner với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy banner", LocalDateTime.now(), null, errorMessages);
        }
        return new ApiResponse<>("success", "Thành công", LocalDateTime.now(), banner, null);
    }

    @Override
    public ApiResponse<Banner> save(Banner banner) {
        Banner savedBanner = bannerRepository.save(banner);
        return new ApiResponse<>("success", "Tạo banner thành công", LocalDateTime.now(), savedBanner, null);
    }

    @Override
    public ApiResponse<Banner> update(Long id, Banner banner) {
        Banner existingBanner = bannerRepository.findById(id).orElse(null);
        if (existingBanner == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy banner với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy banner", LocalDateTime.now(), null, errorMessages);
        }
        existingBanner.setImage_url_banner(banner.getImage_url_banner());
        existingBanner.setIs_status(banner.getIs_status());
        existingBanner.setCategory_id(banner.getCategory_id());
        existingBanner.setUpdated_at(LocalDateTime.now());
        bannerRepository.save(existingBanner);
        return new ApiResponse<>("success", "Cập nhật banner thành công", LocalDateTime.now(), existingBanner, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        Banner existingBanner = bannerRepository.findById(id).orElse(null);
        if (existingBanner == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy banner với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy banner", LocalDateTime.now(), null, errorMessages);
        }

        // Tiến hành xóa banner
        bannerRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa banner thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
