package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.ImageBranch;
import com.example.backend.service.ImageBranchService;
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
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090"}) // Hạn chế CORS cho các origin cụ thể

@RestController
@RequestMapping("/api/image-branches")
public class ImageBranchController {

    private final ImageBranchService imageBranchService;

    public ImageBranchController(ImageBranchService imageBranchService) {
        this.imageBranchService = imageBranchService;
    }

    @GetMapping
    public ApiResponse<List<ImageBranch>> getAllImages() {
        return imageBranchService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<ImageBranch> getImageById(@PathVariable Long id) {
        return imageBranchService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImageBranch>> uploadImage(
            @RequestParam("image_branch") MultipartFile file,
            @RequestParam("is_status") int isStatus,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();

        if (file == null || file.isEmpty()) {
            errors.add("Vui lòng chọn file ảnh");
        } else if (!file.getContentType().startsWith("image/")) {
            errors.add("Chỉ hỗ trợ file ảnh");
        }

        if (isStatus < 0 || isStatus > 1) {
            errors.add("Trạng thái phải là 0 hoặc 1");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
            );
        }

        try {
            String imageUrl = saveFile(file, request);

            ImageBranch imageBranch = new ImageBranch();
            imageBranch.setImage_branch(imageUrl);
            imageBranch.setIs_status(isStatus);
            imageBranch.setCreated_at(LocalDateTime.now());
            imageBranch.setUpdated_at(LocalDateTime.now());

            return ResponseEntity.ok(imageBranchService.save(imageBranch));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi lưu ảnh", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageBranch>> updateImage(
            @PathVariable Long id,
            @RequestParam("image_branch") MultipartFile file,
            @RequestParam("is_status") int isStatus,
            HttpServletRequest request
    ) {
        try {
            ImageBranch existing = imageBranchService.findById(id).getData();
            if (!file.isEmpty() && file.getContentType().startsWith("image/")) {
                deleteOldImage(existing.getImage_branch());
                String imageUrl = saveFile(file, request);
                existing.setImage_branch(imageUrl);
            }

            existing.setIs_status(isStatus);
            existing.setUpdated_at(LocalDateTime.now());

            return ResponseEntity.ok(imageBranchService.update(id, existing));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi cập nhật ảnh", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable Long id) {
        try {
            ImageBranch image = imageBranchService.findById(id).getData();
            deleteOldImage(image.getImage_branch());
            return ResponseEntity.ok(imageBranchService.deleteById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi xóa ảnh", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    // ===== File Handling =====

    private String saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "imageBranch";
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

        return "/uploads/imageBranch/" + fileName;
    }

    private void deleteOldImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "imageBranch";
        String fileName = imageUrl.replace("/uploads/imageBranch/", "");

        String filePath = uploadDir + File.separator + fileName;
        System.out.println(">>> Đường dẫn cần xóa: " + filePath);

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ Đã xóa file: " + filePath);
            } else {
                System.out.println("❌ Không thể xóa file: " + filePath);
            }
        } else {
            System.out.println("⚠️ File không tồn tại tại: " + filePath);
        }
    }
}
