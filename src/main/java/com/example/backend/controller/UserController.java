package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryProduct;
import com.example.backend.entity.SocialToken;
import com.example.backend.entity.User;
import com.example.backend.service.SocialTokenService;
import com.example.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<User>> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // =================== CREATE ===================
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> createUser(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam MultipartFile avatar,
            @RequestParam String phone,
            @RequestParam Long role_id,
            @RequestParam(required = false) LocalDateTime email_verified_at,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String provider_id,
            HttpServletRequest request
    ) {
        List<String> errors = validateUserInput(firstname, email, avatar, lastname, password, phone);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors));
        }

        try {
            String avatarUrl = saveFile(avatar, request);

            User user = new User();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setRole_id(role_id);
            user.setAvatar(avatarUrl);
            user.setEmail_verified_at(email_verified_at);
            user.setProvider(provider);
            user.setProvider_id(provider_id);
            user.setLast_login_at(LocalDateTime.now());
            user.setCreated_at(LocalDateTime.now());
            user.setUpdated_at(LocalDateTime.now());

            ApiResponse<User> response = userService.save(user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi tạo người dùng", LocalDateTime.now(), null, List.of(e.getMessage())));
        }
    }

    // =================== UPDATE ===================
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long id,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam MultipartFile avatar,
            @RequestParam String phone,
            @RequestParam Long role_id,
            @RequestParam(required = false) LocalDateTime email_verified_at,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String provider_id,
            HttpServletRequest request
    ) {
        List<String> errors = validateUserInput(firstname, email, avatar,lastname , password, phone);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors));
        }

        try {
            User user = userService.findById(id).getData();
            if (user == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Người dùng không tồn tại", LocalDateTime.now(), null, List.of("Không tìm thấy người dùng")));
            }

            if (avatar != null && !avatar.isEmpty()) {
                deleteOldImage(user.getAvatar());
                String newAvatarUrl = saveFile(avatar, request);
                user.setAvatar(newAvatarUrl);
            }

            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setRole_id(role_id);
            user.setEmail_verified_at(email_verified_at);
            user.setProvider(provider);
            user.setProvider_id(provider_id);
            user.setLast_login_at(LocalDateTime.now());
            user.setUpdated_at(LocalDateTime.now());

            ApiResponse<User> response = userService.update(id, user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi cập nhật người dùng", LocalDateTime.now(), null, List.of(e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable Long id) {
        try {
            // Tìm user theo ID
            User existingUser = userService.findById(id).getData();

            if (existingUser == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Người dùng không tồn tại", LocalDateTime.now(), null, List.of("Không tìm thấy người dùng"))
                );
            }

            // Xóa avatar nếu có
            String avatarUrl = existingUser.getAvatar();
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                deleteOldImage(avatarUrl);  // Gọi method đã định nghĩa sẵn
            }

            // Xóa người dùng khỏi DB
            userService.deleteById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>("success", "Xóa người dùng thành công", LocalDateTime.now(), "Deleted ID: " + id, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi xóa người dùng", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }


    // =================== VALIDATION ===================
    private List<String> validateUserInput(String firstname, String email, MultipartFile avatar,String lastname , String password, String phone ) {
        List<String> errors = new ArrayList<>();
        if (firstname == null || firstname.trim().isEmpty()) errors.add("firstname không được để trống");
        if (email == null || email.trim().isEmpty()) errors.add("Email không được để trống");
        if (avatar == null || avatar.isEmpty()) errors.add("Vui lòng chọn ảnh đại diện");
        else if (!isImageFile(avatar)) errors.add("Chỉ hỗ trợ file ảnh");

        if(lastname == null || lastname.trim().isEmpty()) errors.add("lastname không được để trống");

        if(password == null || password.trim().isEmpty()) errors.add("password khong duoc de trong!");
        if(phone == null || phone.trim().isEmpty()) errors.add("sdt khong duoc de trong!");
        return errors;
    }

    private boolean isImageFile(MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null && fileType.startsWith("image/");
    }

    // =================== FILE HANDLING ===================
    private String saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "userAvatar";
        File directory = new File(uploadDir);
        if (!directory.exists()) directory.mkdirs();

        String originalFilename = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new IOException("Tên file không hợp lệ"));

        originalFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-]", "_");
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        Path filePath = Paths.get(uploadDir, fileName);
        file.transferTo(filePath.toFile());

        return "/uploads/userAvatar/" + fileName;
    }

    private void deleteOldImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "userAvatar";
        String fileName = imageUrl.replace("/uploads/userAvatar/", "");
        String filePath = uploadDir + File.separator + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ Đã xóa avatar: " + filePath);
            } else {
                System.out.println("❌ Không thể xóa avatar: " + filePath);
            }
        } else {
            System.out.println("⚠️ Avatar không tồn tại tại: " + filePath);
        }
    }
}
