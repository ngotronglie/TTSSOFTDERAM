package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Banner;
import com.example.backend.service.BannerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RestController
@RequestMapping("/api/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public ApiResponse<List<Banner>> getAllBanner() {
        return bannerService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<Banner> getBannerById(@PathVariable Long id) {
        return bannerService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Banner>> uploadBanner(
            @RequestParam("image_url_banner") MultipartFile file,
            @RequestParam("is_status") String isStatus,
            @RequestParam("category_id") String categoryId,
            HttpServletRequest request
    ) {
        try {
            List<String> errors = new ArrayList<>();
            int is_status = 0;
            int category_id = 0;

            // Validate is_status
            if (isStatus == null || isStatus.trim().isEmpty()) {
                errors.add("Trạng thái không được rỗng");
            } else {
                try {
                    is_status = Integer.parseInt(isStatus);
                    if (is_status < 0) {
                        errors.add("Trạng thái không được nhỏ hơn 0");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Trạng thái phải là số hợp lệ");
                }
            }

            // Validate category_id
            if (categoryId == null || categoryId.trim().isEmpty()) {
                errors.add("Category ID không được rỗng");
            } else {
                try {
                    category_id = Integer.parseInt(categoryId);
                    if (category_id <= 0) {
                        errors.add("Category ID phải lớn hơn 0");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Category ID phải là số hợp lệ");
                }
            }

            // Validate file
            if (file == null || file.isEmpty()) {
                errors.add("Vui lòng chọn file ảnh");
            } else if (!file.getContentType().startsWith("image/")) {
                errors.add("Chỉ hỗ trợ file ảnh");
            }

            // Nếu có lỗi -> trả về luôn
            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
                );
            }

            // ----------- LƯU FILE -----------
            String imageUrl = saveFile(file, request);

            // ----------- TẠO BANNER MỚI -----------
            Banner banner = new Banner();
            banner.setImage_url_banner(imageUrl);
            banner.setIs_status(is_status);
            banner.setCategory_id(category_id);

            ApiResponse<Banner> response = bannerService.save(banner);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Đã xảy ra lỗi hệ thống", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Banner>> updateBanner(
            @PathVariable Long id,
            @RequestParam(value = "image_url_banner", required = false) MultipartFile file,
            @RequestParam("is_status") int isStatus,
            @RequestParam("category_id") int categoryId,
            HttpServletRequest request
    ) {
        try {
            // Validate thủ công
            List<String> errors = new ArrayList<>();
            if (isStatus < 0) {
                errors.add("Trạng thái không được nhỏ hơn 0");
            }
            if (categoryId < 0) {
                errors.add("Category ID phải lớn hơn 0");
            }

            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
                );
            }

            Banner existing = bannerService.findById(id).getData();
            if (existing == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Không tìm thấy banner", LocalDateTime.now(), null, List.of("ID: " + id + " không tồn tại"))
                );
            }

            // Nếu có file mới, xử lý thay thế ảnh cũ
            if (file != null && !file.isEmpty()) {
                deleteOldImage(existing.getImage_url_banner());
                String newImageUrl = saveFile(file, request);
                existing.setImage_url_banner(newImageUrl);
            }

            // Cập nhật thông tin khác
            existing.setIs_status(isStatus);
            existing.setCategory_id(categoryId);
            existing.setUpdated_at(LocalDateTime.now());

            Banner updated = bannerService.save(existing).getData();
            return ResponseEntity.ok(
                    new ApiResponse<>("success", "Cập nhật banner thành công", LocalDateTime.now(), updated, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable Long id) {
        try {
            // Lấy banner hiện tại từ DB
            ApiResponse<Banner> existingResponse = bannerService.findById(id);
            Banner existingBanner = existingResponse.getData();

            if (existingBanner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(
                                "error",
                                "Không tìm thấy banner với ID: " + id,
                                LocalDateTime.now(),
                                null,
                                List.of("Banner không tồn tại hoặc đã bị xóa")
                        )
                );
            }

            // Xóa file ảnh khỏi hệ thống
            deleteOldImage(existingBanner.getImage_url_banner());

            // Xóa khỏi database
            bannerService.deleteById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "success",
                            "Xóa banner và ảnh thành công",
                            LocalDateTime.now(),
                            null,
                            null
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            "error",
                            "Lỗi hệ thống khi xóa banner",
                            LocalDateTime.now(),
                            null,
                            List.of(e.getMessage())
                    )
            );
        }
    }


    // Kiểm tra file có phải là ảnh không
    private boolean isImageFile(MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null && fileType.startsWith("image/");
    }

    // Lưu file ảnh vào thư mục uploads/
    private String saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = Paths.get(uploadDir, fileName);
        file.transferTo(filePath.toFile());

        // Đây là phần quan trọng: tạo đường dẫn đầy đủ
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return  "/uploads/" + fileName;
    }


    private void deleteOldImage(String imageUrl) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

        // Kết hợp với tên file để tạo thành đường dẫn đầy đủ
        String pathStr = uploadDir + File.separator + imageUrl.replace("/uploads/", "");
        System.out.println(imageUrl);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(">" + uploadDir);
        System.out.println(">>" +File.separator);
        System.out.println(">>>" +imageUrl.replace("/uploads/", ""));
        System.out.println(">>>>"+pathStr);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Lấy đường dẫn tuyệt đối đến thư mục uploads
//            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

            // Kết hợp với tên file để tạo thành đường dẫn đầy đủ
//            String pathStr = uploadDir + File.separator + imageUrl.replace("/uploads/", "");

            File file = new File(pathStr);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File cũ đã được xóa: " + pathStr);
                } else {
                    System.out.println("Không thể xóa file cũ: " + pathStr);
                }
            }
        }
    }





}
