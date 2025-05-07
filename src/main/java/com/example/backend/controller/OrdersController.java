package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.OrderRequest;
import com.example.backend.entity.Orders;
import com.example.backend.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090"}) // Hạn chế CORS cho các origin cụ thể

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }


    // API tạo đơn hàng (gọi service saveOrder từ OrdersServiceImpl)
    @PostMapping("/user-add")
    public ApiResponse<Orders> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Gọi service và trả luôn response
            return ordersService.saveOrder(orderRequest);
        } catch (Exception e) {
            // Nếu có lỗi, trả về thông báo lỗi
            List<String> errors = new ArrayList<>();
            errors.add("Đã có lỗi xảy ra khi tạo đơn hàng: " + e.getMessage());
            return new ApiResponse<>("error", "Lỗi khi tạo đơn hàng", LocalDateTime.now(), null, errors);
        }
    }


    @GetMapping
    public ApiResponse<List<Orders>> getAllOrders() {
        return ordersService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<Orders> getOrderById(@PathVariable Long id) {
        return ordersService.findById(id);
    }

    @PostMapping
    public ApiResponse<Orders> createOrder(@Valid @RequestBody Orders orders, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors);
        }
        return ordersService.save(orders);
    }





    @PutMapping("/{id}")
    public ApiResponse<Orders> updateOrder(@PathVariable Long id, @Valid @RequestBody Orders orders,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors);
        }
        return ordersService.update(id, orders);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteOrder(@PathVariable Long id) {
        return ordersService.deleteById(id);
    }
}
