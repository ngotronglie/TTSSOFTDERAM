package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.AuthTDO;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.UserTDO;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    @Override
    public ApiResponse<AuthTDO> authenticate(LoginRequest loginRequest) {
        try {
            // Tìm người dùng theo email
            User user = userRepository.findByEmail(loginRequest.getEmail());

            // Kiểm tra sự tồn tại của người dùng
            if (user == null) {
                return new ApiResponse<>(
                        "error",
                        "Người dùng không tồn tại với email: " + loginRequest.getEmail(),
                        LocalDateTime.now(),
                        null,
                        List.of("Người dùng không tồn tại")
                );
            }

            // Kiểm tra mật khẩu
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new ApiResponse<>(
                        "error",
                        "Mật khẩu không đúng",
                        LocalDateTime.now(),
                        null,
                        List.of("Sai mật khẩu")
                );
            }

            // Sinh JWT token
            String token = jwtUtil.generateToken(user.getId_user(), user.getEmail(), user.getRole_id());

            // Tạo DTO trả về (bao gồm thông tin user + token)
            AuthTDO authTDO = new AuthTDO();
            authTDO.setId_user(user.getId_user());
            authTDO.setFirstname(user.getFirstname());
            authTDO.setLastname(user.getLastname());
            authTDO.setEmail(user.getEmail());
            authTDO.setRole_id(user.getRole_id());
            authTDO.setToken(token);

            // Trả về kết quả thành công
            return new ApiResponse<>(
                    "success",
                    "Đăng nhập thành công",
                    LocalDateTime.now(),
                    authTDO,
                    null
            );

        } catch (Exception e) {
            return new ApiResponse<>(
                    "error",
                    "Lỗi khi xác thực người dùng",
                    LocalDateTime.now(),
                    null,
                    List.of(e.getMessage())
            );
        }
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

    // Triển khai phương thức findByEmailAndPassword
    @Override
    public ApiResponse<UserTDO> findByEmailAndPassword(String email, String password) {
        Optional<UserTDO> userOptional = userRepository.findByEmailAndPassword(email, password);

        if (userOptional.isPresent()) {
            return new ApiResponse<>("success", "Đăng nhập thành công", LocalDateTime.now(), userOptional.get(), null);
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Sai email hoặc mật khẩu");
            return new ApiResponse<>("error", "Đăng nhập thất bại", LocalDateTime.now(), null, errorMessages);
        }
    }


}
