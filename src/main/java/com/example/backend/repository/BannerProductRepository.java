package com.example.backend.repository;


import com.example.backend.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerProductRepository extends JpaRepository<Banner, Long> {

}
