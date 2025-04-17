package com.example.backend.service;

import com.example.backend.entity.Cart;
import com.example.backend.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository ) {
        this.cartRepository = cartRepository;
    }
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
