package com.example.backend.service;

import com.example.backend.entity.Orders;
import com.example.backend.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl {
    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders findById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    public void deleteById(Long id) {
        ordersRepository.deleteById(id);
    }
}
