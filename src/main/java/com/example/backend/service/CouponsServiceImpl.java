package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Coupons;
import com.example.backend.repository.CouponsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponsServiceImpl implements CouponsService {

    private final CouponsRepository couponsRepository;

    public CouponsServiceImpl(CouponsRepository couponsRepository) {
        this.couponsRepository = couponsRepository;
    }

    @Override
    public ApiResponse<List<Coupons>> findAll() {
        List<Coupons> coupons = couponsRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách mã giảm giá thành công", LocalDateTime.now(), coupons, null);
    }

    @Override
    public ApiResponse<Coupons> findById(Long id) {
        Coupons coupon = couponsRepository.findById(id).orElse(null);

        if (coupon == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy mã giảm giá với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy mã giảm giá", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Mã giảm giá được tìm thấy", LocalDateTime.now(), coupon, null);
    }

    @Override
    public ApiResponse<Coupons> save(Coupons coupon) {
        Coupons saved = couponsRepository.save(coupon);
        return new ApiResponse<>("success", "Tạo mã giảm giá thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<Coupons> update(Long id, Coupons updatedCoupon) {
        Coupons existingCoupon = couponsRepository.findById(id).orElse(null);

        if (existingCoupon == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy mã giảm giá với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy mã giảm giá", LocalDateTime.now(), null, errorMessages);
        }

        existingCoupon.setCode(updatedCoupon.getCode());
        existingCoupon.setDiscount_type(updatedCoupon.getDiscount_type());
        existingCoupon.setDiscount_value(updatedCoupon.getDiscount_value());
        existingCoupon.setMin_discount(updatedCoupon.getMin_discount());
        existingCoupon.setMax_discount(updatedCoupon.getMax_discount());
        existingCoupon.setMin_order_value(updatedCoupon.getMin_order_value());
        existingCoupon.setStart_date(updatedCoupon.getStart_date());
        existingCoupon.setExpiry_date(updatedCoupon.getExpiry_date());
        existingCoupon.setUsage_limit(updatedCoupon.getUsage_limit());
        existingCoupon.setUsed_count(updatedCoupon.getUsed_count());
        existingCoupon.setCreated_at(updatedCoupon.getCreated_at());

        couponsRepository.save(existingCoupon);

        return new ApiResponse<>("success", "Cập nhật mã giảm giá thành công", LocalDateTime.now(), existingCoupon, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        Coupons existingCoupon = couponsRepository.findById(id).orElse(null);

        if (existingCoupon == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy mã giảm giá với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy mã giảm giá", LocalDateTime.now(), null, errorMessages);
        }

        couponsRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa mã giảm giá thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
