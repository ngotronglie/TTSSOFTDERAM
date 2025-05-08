package com.example.backend.controller;
import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.UserTDO;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

//@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090",} , allowCredentials = "true") // Hạn chế CORS cho các origin cụ thể
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ApiResponse<List<User>> getAll() {
        System.out.println(userService.findAll());
        return userService.findAll();
    }


//    @GetMapping("/get_userid")


    @GetMapping("/{id}")
    public ApiResponse<User> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/get-username")
    public ResponseEntity<ApiResponse<UserTDO>> getUserFromSession(HttpSession session) {
        ApiResponse<UserTDO> response;
        try {
            Object sessionUser = session.getAttribute("user");

            if (sessionUser instanceof UserTDO user) {
                // Tìm thấy người dùng và ép kiểu thành công (Java 16+ hỗ trợ instanceof pattern matching)
                response = new ApiResponse<>(
                        "success",
                        "Lấy thông tin người dùng thành công",
                        LocalDateTime.now(),
                        user,
                        null
                );
            } else {
                // Không có hoặc sai kiểu dữ liệu
                response = new ApiResponse<>(
                        "error",
                        "Không có người dùng đăng nhập",
                        LocalDateTime.now(),
                        null,
                        List.of("User not found or invalid type in session")
                );
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Lỗi hệ thống không mong muốn
            ApiResponse<UserTDO> errorResponse = new ApiResponse<>(
                    "error",
                    "Lỗi hệ thống khi lấy thông tin người dùng từ session",
                    LocalDateTime.now(),
                    null,
                    List.of(e.getMessage())
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }





    // =================== CREATE ===================
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<User>> createUser(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) MultipartFile avatar,
            @RequestParam String phone,
            @RequestParam Long role_id,
            @RequestParam(required = false) LocalDateTime email_verified_at,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String provider_id,
            HttpServletRequest request
    ) {
        System.out.println("==== [CREATE USER REQUEST] ====");
        System.out.println("firstname: " + firstname);
        System.out.println("lastname: " + lastname);
        System.out.println("email: " + email);
        System.out.println("phone: " + phone);
        System.out.println("role_id: " + role_id);
        System.out.println("provider: " + provider);
        System.out.println("provider_id: " + provider_id);

        if (avatar != null && !avatar.isEmpty()) {
            System.out.println("Avatar received: " + avatar.getOriginalFilename() + " (size: " + avatar.getSize() + ")");
        } else {
            System.out.println("No avatar uploaded.");
        }

        List<String> errors = validateUserInput(firstname, email, avatar, lastname, password, phone);
        if (!errors.isEmpty()) {
            System.out.println("Validation errors: " + errors);
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors));
        }

        try {
            String avatarUrl = null;

            if (avatar != null && !avatar.isEmpty()) {
                if (isValidImage(avatar)) {
                    avatarUrl = saveFile(avatar, request);
                    System.out.println("Avatar saved at: " + avatarUrl);
                } else {
                    String err = "File không phải là ảnh hợp lệ.";
                    System.out.println("Error: " + err);
                    errors.add(err);
                    return ResponseEntity.badRequest().body(
                            new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors));
                }
            }

            User user = new User();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setPhone(phone);
            user.setRole_id(role_id);
            user.setAvatar(avatarUrl);
            user.setEmail_verified_at(email_verified_at);
            user.setProvider(provider);
            user.setProvider_id(provider_id);
            user.setLast_login_at(LocalDateTime.now());
            user.setCreated_at(LocalDateTime.now());
            user.setUpdated_at(LocalDateTime.now());

            System.out.println("Saving user: " + user);

            ApiResponse<User> response = userService.save(user);
            System.out.println("User created successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("Lỗi hệ thống khi tạo người dùng: " + e.getMessage());
            e.printStackTrace(); // In stack trace đầy đủ
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi tạo người dùng", LocalDateTime.now(), null, List.of(e.getMessage())));
        }
    }


    // Hàm kiểm tra xem file có phải là ảnh hay không
    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }


    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserTDO>> login(
            @Valid
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session
    ) {
        try {
            // Gọi phương thức findByEmailAndPassword từ UserService
            ApiResponse<UserTDO> response = userService.findByEmailAndPassword(email, password);

            // Kiểm tra nếu không có user trả về thông báo lỗi
            if ("error".equals(response.getStatus())) {
                return ResponseEntity.status(401).body(response);
            }

            // Nếu đăng nhập thành công, lưu thông tin user vào session
            session.setAttribute("user", response.getData());
            // Trả về thông báo thành công và thông tin user
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Xử lý lỗi hệ thống
            ApiResponse<UserTDO> errorResponse = new ApiResponse<>(
                    "error",
                    "Lỗi hệ thống khi đăng nhập",
                    LocalDateTime.now(),
                    null,
                    List.of(e.getMessage())
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    // =================== LOGOUT ===================
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpSession session) {
        try {
            session.invalidate(); // Invalidate the session to log out the user
            return ResponseEntity.ok(new ApiResponse<>("success", "Logout successful", LocalDateTime.now(), "Logged out", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi đăng xuất", LocalDateTime.now(), null, List.of(e.getMessage())));
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
//            user.setPassword(password);
            user.setPassword(passwordEncoder.encode(password));
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
        if(lastname == null || lastname.trim().isEmpty()) errors.add("lastname không được để trống");
        if(password == null || password.trim().isEmpty()) errors.add("password khong duoc de trong!");
//        if(phone == null || phone.trim().isEmpty()) errors.add("sdt khong duoc de trong!");
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
