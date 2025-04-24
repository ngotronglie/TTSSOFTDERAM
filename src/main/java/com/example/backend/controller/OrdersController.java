package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Orders;
import com.example.backend.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
