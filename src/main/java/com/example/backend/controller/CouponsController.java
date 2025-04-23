package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Coupons;
import com.example.backend.service.CouponsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponsController {

    private final CouponsService couponsService;

    public CouponsController(CouponsService couponsService) {
        this.couponsService = couponsService;
    }

    @GetMapping("")
    public ApiResponse<List<Coupons>> getAllCoupons() {
        return couponsService.findAll();
    }

    // Tạo mới coupon
    @PostMapping
    public ResponseEntity<ApiResponse<Coupons>> createCoupon(@Valid @RequestBody Coupons coupon) {
        ApiResponse<Coupons> response = couponsService.save(coupon);
        return ResponseEntity.ok(response);
    }

    // Lấy thông tin coupon theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Coupons>> getCouponById(@PathVariable Long id) {
        ApiResponse<Coupons> response = couponsService.findById(id);
        return ResponseEntity.ok(response);
    }

    // Cập nhật coupon
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Coupons>> updateCoupon(@PathVariable Long id, @Valid @RequestBody Coupons coupon) {
        ApiResponse<Coupons> response = couponsService.update(id, coupon);
        return ResponseEntity.ok(response);
    }

    // Xoá coupon
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCoupon(@PathVariable Long id) {
        ApiResponse<String> response = couponsService.deleteById(id);
        return ResponseEntity.ok(response);
    }
}
