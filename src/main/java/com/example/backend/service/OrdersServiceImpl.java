package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.OrderDetailRequest;
import com.example.backend.dto.OrderRequest;
import com.example.backend.entity.Orders;
import com.example.backend.entity.OrderDetail;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.OrderDetailRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrderDetailRepository orderDetailRepository) {
        this.ordersRepository = ordersRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    // Phương thức saveOrder tích hợp với OrderRequest từ frontend
    public ApiResponse<Orders> saveOrder(OrderRequest orderRequest) {
        // Tạo đối tượng đơn hàng mới
        Orders order = new Orders();
        order.setUser_id(orderRequest.getUserId().intValue());
        order.setFull_name(orderRequest.getFullName());
        order.setEmail(orderRequest.getEmail());
        order.setPhone(orderRequest.getPhoneNumber());
        order.setAddress(orderRequest.getAddress());
        order.setMethod(orderRequest.getPaymentMethod());
        order.setTotal_price(orderRequest.getTotalPrice());
        order.setStatus_orders("pending"); // Trạng thái đơn hàng mặc định là pending
        order.setCode(UUID.randomUUID().toString().substring(0, 8)); // Mã đơn hàng
        order.setCreated_at(LocalDateTime.now()); // Thời gian tạo đơn hàng

        // Lưu đơn hàng vào cơ sở dữ liệu
        Orders savedOrder = ordersRepository.save(order);

        // Lưu chi tiết đơn hàng từ orderRequest.getOrderDetails()
        for (OrderDetailRequest detailRequest : orderRequest.getOrderDetails()) {
            // Tạo một đối tượng OrderDetail mới
            OrderDetail orderDetail = new OrderDetail();

            // Gán các thuộc tính của OrderDetail từ OrderDetailRequest
            orderDetail.setOrder_id(savedOrder.getId_order().intValue()); // Gán ID đơn hàng cho chi tiết đơn hàng
            orderDetail.setProduct_id(detailRequest.getProductId()); // Gán ID sản phẩm
            orderDetail.setQuantity(detailRequest.getQuantity()); // Gán số lượng
            orderDetail.setPrice(detailRequest.getPrice()); // Gán giá

            // Cập nhật thời gian tạo và sửa đổi
            orderDetail.setCreated_at(LocalDateTime.now());
            orderDetail.setUpdated_at(LocalDateTime.now());

            // Lưu chi tiết đơn hàng vào cơ sở dữ liệu
            orderDetailRepository.save(orderDetail);
        }


        return new ApiResponse<>("success", "Đặt hàng thành công", LocalDateTime.now(), savedOrder, null);
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
