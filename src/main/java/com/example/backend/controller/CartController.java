package com.example.backend.controller;


import com.example.backend.entity.Cart;
import com.example.backend.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCategoriesProducts() {
        return cartService.findAll();
    }

    @GetMapping("/{id}")
    public Cart getCategoriesProductById(@PathVariable Long id) {
        return cartService.findById(id);
    }

    @PostMapping
    public Cart createCategoriesProduct(@RequestBody Cart cart) {

        return cartService.save(cart);
    }

    @PutMapping("/{id}")
    public Cart updateBanner(@PathVariable Long id, @RequestBody Cart cart) {
        Cart existingCart = cartService.findById(id);
        if (existingCart == null) {
            throw new EntityNotFoundException("Banner with id " + id + " not found");
        }

        existingCart.setUser_id(cart.getUser_id());
        existingCart.setProduct_id(cart.getProduct_id());
        existingCart.setQuantity(cart.getQuantity());
        existingCart.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return cartService.save(existingCart);
    }

    @DeleteMapping("/{id}")
    public void deleteBanner(@PathVariable Long id) {
        cartService.deleteById(id);
    }

}
