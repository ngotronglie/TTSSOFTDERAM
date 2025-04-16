package com.example.backend.controller;
import com.example.backend.entity.Banner;
import com.example.backend.service.BannerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public List<Banner> getAllCategoriesProducts() {
        return bannerService.findAll();
    }

    @GetMapping("/{id}")
    public Banner getCategoriesProductById(@PathVariable Long id) {
        return bannerService.findById(id);
    }

    @PostMapping
    public Banner createCategoriesProduct(@RequestBody Banner banner) {
        return bannerService.save(banner);
    }

    @PutMapping("/{id}")
    public Banner updateBanner(@PathVariable Long id, @RequestBody Banner banner) {
        Banner existingBanner = bannerService.findById(id);
        if (existingBanner == null) {
            throw new EntityNotFoundException("Banner with id " + id + " not found");
        }

        existingBanner.setImage_url_banner(banner.getImage_url_banner());
        existingBanner.setIs_status(banner.getIs_status());
        existingBanner.setCategory_id(banner.getCategory_id());
        existingBanner.setCreated_at(banner.getCreated_at());
        existingBanner.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        return bannerService.save(existingBanner);
    }

    @DeleteMapping("/{id}")
    public void deleteBanner(@PathVariable Long id) {
        bannerService.deleteById(id);
    }

}
