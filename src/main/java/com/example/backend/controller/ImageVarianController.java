package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.ImageVarian;
import com.example.backend.service.ImageVarianService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/image-varians")
public class ImageVarianController {

    private final ImageVarianService imageVarianService;

    public ImageVarianController(ImageVarianService imageVarianService) {
        this.imageVarianService = imageVarianService;
    }

    @GetMapping
    public ApiResponse<List<ImageVarian>> getAllImages() {
        return imageVarianService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<ImageVarian> getImageById(@PathVariable Long id) {
        return imageVarianService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImageVarian>> uploadImage(
            @RequestParam("image_url") MultipartFile file,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();

        if (file == null || file.isEmpty()) {
            errors.add("Vui lòng chọn file ảnh");
        } else if (!file.getContentType().startsWith("image/")) {
            errors.add("Chỉ hỗ trợ file ảnh");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
            );
        }

        try {
            String imageUrl = saveFile(file, request);

            ImageVarian imageVarian = new ImageVarian();
            imageVarian.setImage_url(imageUrl);
            imageVarian.setCreated_at(LocalDateTime.now());
            imageVarian.setUpdated_at(LocalDateTime.now());

            return ResponseEntity.ok(imageVarianService.save(imageVarian));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi lưu ảnh", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageVarian>> updateImage(
            @PathVariable Long id,
            @RequestParam("image_url") MultipartFile file,
            HttpServletRequest request
    ) {
        try {
            ImageVarian existing = imageVarianService.findById(id).getData();
            if (existing == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Không tìm thấy ảnh", LocalDateTime.now(), null, List.of("Không tìm thấy ảnh"))
                );
            }

            if (!file.isEmpty() && file.getContentType().startsWith("image/")) {
                deleteOldImage(existing.getImage_url());
                String imageUrl = saveFile(file, request);
                existing.setImage_url(imageUrl);
                existing.setUpdated_at(LocalDateTime.now());
            }

            return ResponseEntity.ok(imageVarianService.update(id, existing));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi cập nhật ảnh", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable Long id) {
        try {
            ImageVarian image = imageVarianService.findById(id).getData();
            if (image == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Không tìm thấy ảnh", LocalDateTime.now(), null, List.of("Không tìm thấy ảnh"))
                );
            }

            deleteOldImage(image.getImage_url());
            return ResponseEntity.ok(imageVarianService.deleteById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi xóa ảnh", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    // ===== File Handling =====

    private String saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "imageVarian";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Tên file không hợp lệ");
        }

        originalFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-]", "_");
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        Path filePath = Paths.get(uploadDir, fileName);
        file.transferTo(filePath.toFile());

        return "/uploads/imageVarian/" + fileName;
    }

    private void deleteOldImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "imageVarian";
        String fileName = imageUrl.replace("/uploads/imageVarian/", "");
        String filePath = uploadDir + File.separator + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ Đã xóa ảnh: " + filePath);
            } else {
                System.out.println("❌ Không thể xóa ảnh: " + filePath);
            }
        }
    }
}