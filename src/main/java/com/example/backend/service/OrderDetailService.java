package com.example.backend.service;

import com.example.backend.entity.OrderDetail;
import com.example.backend.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }
    public OrderDetail findById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
