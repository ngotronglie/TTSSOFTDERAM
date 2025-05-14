package com.example.backend.controller;


import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.CartDetailDTO;
import com.example.backend.dto.CartRequestDTO;
import com.example.backend.entity.Cart;
import com.example.backend.entity.City;
import com.example.backend.service.CartService;
import com.example.backend.service.CartServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090"}) // Hạn chế CORS cho các origin cụ thể
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ApiResponse<List<Cart>> getAllCart() {
        return cartService.findAll();
    }
    @GetMapping("/{id}")
    public ApiResponse<Cart> getCartById(@PathVariable Long id) {
        return cartService.findById(id);
    }


    @PostMapping
    public ApiResponse<Cart> createCart(@RequestBody Cart cart, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage) // Lấy thông báo lỗi của mỗi trường
                    .collect(Collectors.toList());
            return new ApiResponse<>(
                    "error",
                    "Dữ liệu không hợp lệ",
                    LocalDateTime.now(),
                    null,
                    errorMessages // Trả về danh sách lỗi
            );
        }
        return cartService.save(cart);
    }

    @PutMapping("/{id}")
    public ApiResponse<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Lấy tất cả các lỗi và trả về chúng dưới dạng mảng
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ApiResponse<>(
                    "error",
                    "Dữ liệu không hợp lệ",
                    LocalDateTime.now(),
                    null,
                    errorMessages // Trả về mảng lỗi
            );
        }

        return cartService.update(id, cart);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCart(@PathVariable Long id) {
        return cartService.deleteById(id);
    }

    @PostMapping("/add")
    public ApiResponse<Cart> addToCart(@Valid @RequestBody CartRequestDTO cartRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors);
        }
        return cartService.addToCart(cartRequest);
    }

    @PutMapping("/update")
    public ApiResponse<Cart> updateCartItem(@Valid @RequestBody CartRequestDTO cartRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors);
        }
        return cartService.updateCartItem(cartRequest);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ApiResponse<String> removeFromCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        return cartService.removeFromCart(userId, productId);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<CartDetailDTO>> getUserCart(@PathVariable Integer userId) {
        return cartService.getUserCart(userId);
    }
}
