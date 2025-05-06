package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Orders;
import com.example.backend.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public ApiResponse<List<Orders>> findAll() {
        List<Orders> orders = ordersRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách đơn hàng thành công", LocalDateTime.now(), orders, null);
    }
    @Override
    public ApiResponse<Orders> findById(Long id) {
        Orders order = ordersRepository.findById(id).orElse(null);
        if (order == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Không tìm thấy đơn hàng với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy đơn hàng", LocalDateTime.now(), null, errors);
        }
        return new ApiResponse<>("success", "Đơn hàng được tìm thấy", LocalDateTime.now(), order, null);
    }

    @Override
    public ApiResponse<Orders> save(Orders orders) {
        orders.setCode(UUID.randomUUID().toString().substring(0, 8));
        orders.setCreated_at(LocalDateTime.now());
        Orders saved = ordersRepository.save(orders);
        return new ApiResponse<>("success", "Tạo đơn hàng thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<Orders> update(Long id, Orders orders) {
        Orders existing = ordersRepository.findById(id).orElse(null);
        if (existing == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Không tìm thấy đơn hàng với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy đơn hàng", LocalDateTime.now(), null, errors);
        }

        existing.setUser_id(orders.getUser_id());
        existing.setTotal_price(orders.getTotal_price());
        existing.setCoupon_id(orders.getCoupon_id());
        existing.setStatus_orders(orders.getStatus_orders());
        existing.setFull_name(orders.getFull_name());
        existing.setPhone(orders.getPhone());
        existing.setEmail(orders.getEmail());
        existing.setAddress(orders.getAddress());
        existing.setUpdated_at(LocalDateTime.now());

        Orders updated = ordersRepository.save(existing);
        return new ApiResponse<>("success", "Cập nhật đơn hàng thành công", LocalDateTime.now(), updated, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        // Kiểm tra xem đơn hàng có tồn tại hay không
        Orders existing = ordersRepository.findById(id).orElse(null);

        // Nếu không tìm thấy đơn hàng
        if (existing == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Không tìm thấy đơn hàng với id: " + id);

            // Trả về thông báo lỗi với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy đơn hàng", LocalDateTime.now(), null, errors);
        }

        // Xóa đơn hàng
        ordersRepository.deleteById(id);

        // Trả về thông báo thành công khi xóa thành công
        return new ApiResponse<>("success", "Xóa đơn hàng thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
