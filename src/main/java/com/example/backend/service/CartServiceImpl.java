package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Cart;
import com.example.backend.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository ) {
        this.cartRepository = cartRepository;
    }

    @Override
    public ApiResponse<List<Cart>> findAll() {
        List<Cart> cart = cartRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách gio hang thành công", LocalDateTime.now(), cart, null);
    }

    @Override
    public ApiResponse<Cart> findById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);

        if (cart == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy gio hang");
            return new ApiResponse<>("error", "Không tìm thấy gio hang", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Thành phố được tìm thấy", LocalDateTime.now(), cart, null);
    }

    @Override
    public ApiResponse<Cart> save(Cart cart) {
        Cart savedCart = cartRepository.save(cart);
        return new ApiResponse<>("success", "Tạo gio hang thành công", LocalDateTime.now(), savedCart, null);
    }

    @Override
    public ApiResponse<Cart> update(Long id, Cart cart) {
        Cart existing = cartRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy gio hang với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy gio hang", LocalDateTime.now(), null, errorMessages);
        }

//        existing.setNameCity(city.getNameCity());
        cartRepository.save(existing);

        return new ApiResponse<>("success", "Cập nhật gio hang thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        Cart existing = cartRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy gio hang với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy gio hang", LocalDateTime.now(), null, errorMessages);
        }
        cartRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa gio hang thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
