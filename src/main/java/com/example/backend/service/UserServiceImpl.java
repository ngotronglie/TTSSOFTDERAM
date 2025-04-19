package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse<List<User>> findAll() {
        List<User> users = userRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách người dùng thành công", LocalDateTime.now(), users, null);
    }

    @Override
    public ApiResponse<User> findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy người dùng với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy người dùng", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Người dùng được tìm thấy", LocalDateTime.now(), user, null);
    }

    @Override
    public ApiResponse<User> save(User user) {
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return new ApiResponse<>("success", "Tạo người dùng thành công", LocalDateTime.now(), savedUser, null);
    }

    @Override
    public ApiResponse<User> update(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy người dùng với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy người dùng", LocalDateTime.now(), null, errorMessages);
        }

        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setLastname(updatedUser.getLastname());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setRole_id(updatedUser.getRole_id());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setAvatar(updatedUser.getAvatar());
        existingUser.setUpdated_at(LocalDateTime.now());

        userRepository.save(existingUser);
        return new ApiResponse<>("success", "Cập nhật người dùng thành công", LocalDateTime.now(), existingUser, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy người dùng với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy người dùng", LocalDateTime.now(), null, errorMessages);
        }

        userRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa người dùng thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
