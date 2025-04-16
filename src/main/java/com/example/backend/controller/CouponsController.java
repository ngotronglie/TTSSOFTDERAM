package com.example.backend.controller;

import com.example.backend.entity.Coupons;
import com.example.backend.service.CouponsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponsController {

    private final CouponsService couponsService;

    public CouponsController(CouponsService couponsService) {
        this.couponsService = couponsService;
    }

    // Lấy tất cả coupons
    @GetMapping
    public List<Coupons> getAllCoupons() {
        return couponsService.findAll();
    }

    // Lấy coupon theo ID
    @GetMapping("/{id}")
    public Coupons getCouponById(@PathVariable Long id) {
        Coupons coupon = couponsService.findById(id);
        if (coupon == null) {
            throw new EntityNotFoundException("Coupon with id " + id + " not found");
        }
        return coupon;
    }

    // Tạo mới coupon
    @PostMapping
    public Coupons createCoupon(@RequestBody Coupons coupon) {
        coupon.setCreated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return couponsService.save(coupon);
    }
    // Cập nhật coupon
    @PutMapping("/{id}")
    public Coupons updateCoupon(@PathVariable Long id, @RequestBody Coupons coupon) {
        Coupons existing = couponsService.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException("Coupon with id " + id + " not found");
        }
        existing.setCode(coupon.getCode());
        existing.setDiscount_type(coupon.getDiscount_type());
        existing.setDiscount_value(coupon.getDiscount_value());
        existing.setMin_discount(coupon.getMin_discount());
        existing.setMax_discount(coupon.getMax_discount());
        existing.setMin_order_value(coupon.getMin_order_value());
        existing.setStart_date(coupon.getStart_date());
        existing.setExpiry_date(coupon.getExpiry_date());
        existing.setUsage_limit(coupon.getUsage_limit());
        existing.setUsed_count(coupon.getUsed_count());
        return couponsService.save(existing);
    }

    // Xóa coupon
    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable Long id) {
        couponsService.deleteById(id);
    }
}
