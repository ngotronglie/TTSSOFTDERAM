package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.OrderDetail;
import com.example.backend.service.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090"}) // Hạn chế CORS cho các origin cụ thể

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    // Lấy danh sách tất cả chi tiết đơn hàng
    @GetMapping
    public ApiResponse<List<OrderDetail>> getAllOrderDetails() {
        return orderDetailService.findAll();
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ApiResponse<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        return orderDetailService.findById(id);
    }

    // Tạo mới chi tiết đơn hàng
    @PostMapping
    public ApiResponse<OrderDetail> createOrderDetail(@Valid @RequestBody OrderDetail orderDetail,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ApiResponse<>(
                    "error",
                    "Dữ liệu không hợp lệ",
                    LocalDateTime.now(),
                    null,
                    errorMessages
            );
        }

        return orderDetailService.save(orderDetail);
    }

    // Cập nhật chi tiết đơn hàng theo ID
    @PutMapping("/{id}")
    public ApiResponse<OrderDetail> updateOrderDetail(@PathVariable Long id,
                                                      @Valid @RequestBody OrderDetail orderDetail,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ApiResponse<>(
                    "error",
                    "Dữ liệu không hợp lệ",
                    LocalDateTime.now(),
                    null,
                    errorMessages
            );
        }

        return orderDetailService.update(id, orderDetail);
    }

    // Xóa chi tiết đơn hàng theo ID
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteOrderDetail(@PathVariable Long id) {
        return orderDetailService.deleteById(id);
    }
}
