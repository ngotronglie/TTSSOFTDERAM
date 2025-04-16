package com.example.backend.repository;

import com.example.backend.entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponsRepository extends JpaRepository<Coupons, Long> {
}
