package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_coupon")
    @SequenceGenerator(name = "id_coupon", sequenceName = "COUPONS_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_coupon;

    @Column(name = "code", unique = true)
    @NotEmpty(message = "Mã giảm giá không được để trống!")
    @Size(min = 3, max = 20, message = "Mã giảm giá phải có từ 3 đến 20 ký tự!")
    private String code;

    @Column(name = "discount_type", unique = true)
    @NotEmpty(message = "Tên loại giảm giá không được để trống!")
    @Size(min = 3, max = 50, message = "Tên loại giảm giá phải có từ 3 đến 50 ký tự!")
    private String discount_type;

    @Column(name = "discount_value")
    @NotNull(message = "Giá giảm giá không được để trống!")
    @DecimalMin(value = "0.01", message = "Giá giảm giá phải lớn hơn 0!")
    private BigDecimal discount_value;

    @Column(name = "min_discount")
    @NotNull(message = "Giá giảm giá min không được để trống!")
    @DecimalMin(value = "0.01", message = "Giá giảm giá min phải lớn hơn 0!")
    private BigDecimal min_discount;

    @Column(name = "max_discount")
    @NotNull(message = "Giá giảm giá max không được để trống!")
    @DecimalMin(value = "0.01", message = "Giá giảm giá max phải lớn hơn 0!")
    private BigDecimal max_discount;

    @Column(name = "min_order_value")
    @NotNull(message = "Giá trị đơn hàng min không được để trống!")
    @DecimalMin(value = "0.01", message = "Giá trị đơn hàng min phải lớn hơn 0!")
    private BigDecimal min_order_value;

    @Column(name = "start_date")
    @NotNull(message = "Ngày bắt đầu giảm giá không được để trống!")
    private LocalDate start_date;

    @Column(name = "expiry_date")
    @NotNull(message = "Ngày hết hạn giảm giá không được để trống!")
    private LocalDate expiry_date;
    @Column(name = "usage_limit")
    @Min(value = 1, message = "Số lượng sử dụng tối đa phải lớn hơn 0!")
    private int usage_limit;

    @Column(name = "used_count")
    @Min(value = 0, message = "Số lượng đã sử dụng không được nhỏ hơn 0!")
    private int used_count;

    @Column(name = "created_at")
    @PastOrPresent(message = "Ngày tạo phải là ngày trong quá khứ hoặc hiện tại!")
    private LocalDate created_at;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public BigDecimal getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(BigDecimal discount_value) {
        this.discount_value = discount_value;
    }

    public LocalDate getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDate expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Long getId_coupon() {
        return id_coupon;
    }

    public void setId_coupon(Long id_coupon) {
        this.id_coupon = id_coupon;
    }

    public BigDecimal getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(BigDecimal max_discount) {
        this.max_discount = max_discount;
    }

    public BigDecimal getMin_discount() {
        return min_discount;
    }

    public void setMin_discount(BigDecimal min_discount) {
        this.min_discount = min_discount;
    }

    public BigDecimal getMin_order_value() {
        return min_order_value;
    }

    public void setMin_order_value(BigDecimal min_order_value) {
        this.min_order_value = min_order_value;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public int getUsage_limit() {
        return usage_limit;
    }

    public void setUsage_limit(int usage_limit) {
        this.usage_limit = usage_limit;
    }

    public int getUsed_count() {
        return used_count;
    }

    public void setUsed_count(int used_count) {
        this.used_count = used_count;
    }
}
