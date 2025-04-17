package com.example.backend.controller;


import com.example.backend.entity.Cart;
import com.example.backend.service.CartServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    public CartController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @GetMapping
    public List<Cart> getAllCart() {
        return cartServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartServiceImpl.findById(id);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {

        return cartServiceImpl.save(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        Cart existingCart = cartServiceImpl.findById(id);
        if (existingCart == null) {
            throw new EntityNotFoundException("Banner with id " + id + " not found");
        }

        existingCart.setUser_id(cart.getUser_id());
        existingCart.setProduct_id(cart.getProduct_id());
        existingCart.setQuantity(cart.getQuantity());
        existingCart.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return cartServiceImpl.save(existingCart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartServiceImpl.deleteById(id);
    }

}
