package com.example.backend.controller;

import com.example.backend.entity.Orders;
import com.example.backend.service.OrdersServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersServiceImpl ordersServiceImpl;

    @Autowired
    public OrdersController(OrdersServiceImpl ordersServiceImpl) {
        this.ordersServiceImpl = ordersServiceImpl;
    }

    // Lấy tất cả đơn hàng
    @GetMapping
    public List<Orders> getAllOrders() {
        return ordersServiceImpl.findAll();
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public Orders getOrderById(@PathVariable Long id) {
        Orders order = ordersServiceImpl.findById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
        return order;
    }

    // Tạo mới đơn hàng
    @PostMapping
    public Orders createOrder(@RequestBody Orders order) {
        order.setCreated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        order.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return ordersServiceImpl.save(order);
    }

    // Cập nhật đơn hàng
    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        Orders existingOrder = ordersServiceImpl.findById(id);
        if (existingOrder == null) {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
        existingOrder.setUser_id(updatedOrder.getUser_id());
        existingOrder.setTotal_price(updatedOrder.getTotal_price());
        existingOrder.setCoupon_id(updatedOrder.getCoupon_id());
        existingOrder.setStatus_orders(updatedOrder.getStatus_orders());
        existingOrder.setFull_name(updatedOrder.getFull_name());
        existingOrder.setPhone(updatedOrder.getPhone());
        existingOrder.setEmail(updatedOrder.getEmail());
        existingOrder.setAddress(updatedOrder.getAddress());
        existingOrder.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return ordersServiceImpl.save(existingOrder);
    }

    // Xóa đơn hàng
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        ordersServiceImpl.deleteById(id);
    }
}
