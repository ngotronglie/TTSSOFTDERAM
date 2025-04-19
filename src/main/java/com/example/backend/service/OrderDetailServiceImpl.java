package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.OrderDetail;
import com.example.backend.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository ) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public ApiResponse<List<OrderDetail>> findAll() {
        List<OrderDetail> orderdetail = orderDetailRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách order detail thành công", LocalDateTime.now(), orderdetail, null);
    }

    @Override
    public ApiResponse<OrderDetail> findById(Long id) {
        OrderDetail orderdetail = orderDetailRepository.findById(id).orElse(null);

        if (orderdetail == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy order detail với id: " + id);

            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy order detail", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "order detail được tìm thấy", LocalDateTime.now(), orderdetail, null);
    }

    @Override
    public ApiResponse<OrderDetail> save(OrderDetail orderdetail) {
        OrderDetail saveOrderDetail = orderDetailRepository.save(orderdetail);
        return new ApiResponse<>("success", "Tạo order detail thành công", LocalDateTime.now(), saveOrderDetail, null);
    }

    @Override
    public ApiResponse<OrderDetail> update(Long id, OrderDetail orderdetail) {
        OrderDetail existing = orderDetailRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy order detail với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy order detail", LocalDateTime.now(), null, errorMessages);
        }

        existing.setOrder_id(orderdetail.getOrder_id());
        existing.setProduct_id(orderdetail.getProduct_id());
        existing.setQuantity(orderdetail.getQuantity());
        existing.setPrice(orderdetail.getPrice());
        existing.setUpdated_at( LocalDateTime.now());
        orderDetailRepository.save(existing);

        return new ApiResponse<>("success", "Cập nhật order detail thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        OrderDetail existing = orderDetailRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy order detail với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy order detail", LocalDateTime.now(), null, errorMessages);
        }
        orderDetailRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa order detail thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
