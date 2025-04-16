package com.example.backend.controller;

import com.example.backend.entity.OrderDetail;
import com.example.backend.entity.Orders;
import com.example.backend.service.OrderDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    // Lấy tất cả chi tiết đơn hàng
    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.findAll();
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        if (orderDetail == null) {
            throw new EntityNotFoundException("OrderDetail with id " + id + " not found");
        }
        return ResponseEntity.ok(orderDetail);
    }

    // Tạo mới chi tiết đơn hàng
    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        // Thiết lập thời gian tạo và cập nhật
        orderDetail.setCreated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        orderDetail.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        // Lưu đơn hàng chi tiết vào cơ sở dữ liệu
        OrderDetail savedOrderDetail = orderDetailService.save(orderDetail);

        // Trả về ResponseEntity với mã trạng thái 201 (Created) và đối tượng đã lưu
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetail);
    }


    // Cập nhật chi tiết đơn hàng
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail updatedOrderDetail) {
        OrderDetail existingOrderDetail = orderDetailService.findById(id);
        if (existingOrderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu không tìm thấy
        }

        existingOrderDetail.setOrder_id(updatedOrderDetail.getOrder_id());
        existingOrderDetail.setProduct_id(updatedOrderDetail.getProduct_id());
        existingOrderDetail.setQuantity(updatedOrderDetail.getQuantity());
        existingOrderDetail.setPrice(updatedOrderDetail.getPrice());
        existingOrderDetail.setUpdated_at(LocalDateTime.now());
        OrderDetail savedOrderDetail = orderDetailService.save(existingOrderDetail);
        return ResponseEntity.ok(savedOrderDetail);
    }

    // Xóa chi tiết đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        OrderDetail existingOrderDetail = orderDetailService.findById(id);
        if (existingOrderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }

        orderDetailService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Trả về 204 khi xóa thành công
    }
}
