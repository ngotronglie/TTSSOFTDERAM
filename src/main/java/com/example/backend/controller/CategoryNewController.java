package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryNew;
import com.example.backend.service.CategoryNewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories-new")
public class CategoryNewController {

    private final CategoryNewService categoryNewService;

    public CategoryNewController(CategoryNewService categoryNewService) {
        this.categoryNewService = categoryNewService;
    }

    // =================== CRUD ===================

    @GetMapping
    public ApiResponse<List<CategoryNew>> getAllCategoriesProducts() {
        return categoryNewService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryNew> getCategoriesProductById(@PathVariable Long id) {
        return categoryNewService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryNew>> createCategoryProductWithImage(
            @RequestParam("name_category_new") String name_category_new,
            @RequestParam("parent_category_new_id") int parent_category_new_id,
            @RequestParam("image_category_new") MultipartFile file,
            @RequestParam("is_active") int isActive,
            HttpServletRequest request
    ) {
        try {

            List<String> errors = new ArrayList<>();

            if(name_category_new == null || name_category_new.trim().isEmpty()) {
                errors.add("Vui long nhap ten danh muc");
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



            // ===== LƯU FILE =====
            String imageUrl = saveFile(file, request);

            // ===== TẠO CATEGORY PRODUCT =====
            CategoryNew categoryNew = new CategoryNew();
            categoryNew.setName_category_new(name_category_new);
            categoryNew.setParent_category_new_id(parent_category_new_id);
            categoryNew.setImage_category_new(imageUrl);
            categoryNew.setIs_active(isActive);
            // Lưu CategoryProduct vào database
            ApiResponse<CategoryNew> response = categoryNewService.save(categoryNew);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Trả về lỗi khi có sự cố trong quá trình lưu
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi tạo danh mục", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryNew>> updateCategoryNewWithImage(
            @PathVariable Long id,
            @RequestParam("name_category_new") String nameCategoryNew,
            @RequestParam("parent_category_new_id") int parentCategoryNewId,
            @RequestParam("image_category_new") MultipartFile file,
            @RequestParam("is_active") int isActive,
            HttpServletRequest request
    ) {
        try {
            // Tìm kiếm CategoryNew theo ID
            CategoryNew existingCategoryNew = categoryNewService.findById(id).getData();
            if (existingCategoryNew == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Danh mục không tồn tại", LocalDateTime.now(), null, List.of("Danh mục không tìm thấy"))
                );
            }

            // Kiểm tra và xóa ảnh cũ nếu có
            if (file != null && !file.isEmpty()) {
                deleteOldImage(existingCategoryNew.getImage_category_new()); // Xóa ảnh cũ nếu có
                String newImageUrl = saveFile(file, request);  // Lưu ảnh mới
                existingCategoryNew.setImage_category_new(newImageUrl);  // Cập nhật ảnh mới
            }

            // Cập nhật các trường dữ liệu khác
            existingCategoryNew.setName_category_new(nameCategoryNew);
            existingCategoryNew.setParent_category_new_id(parentCategoryNewId);
            existingCategoryNew.setUpdated_at(LocalDateTime.now());  // Cập nhật thời gian
            existingCategoryNew.setIs_active(isActive);

            // Lưu thay đổi vào cơ sở dữ liệu
            ApiResponse<CategoryNew> response = categoryNewService.update(id, existingCategoryNew);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi cập nhật danh mục", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategoryNew(@PathVariable Long id) {
        try {
            // Kiểm tra xem CategoryNew với id có tồn tại không
            CategoryNew existingCategoryNew = categoryNewService.findById(id).getData();

            if (existingCategoryNew == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Danh mục không tồn tại", LocalDateTime.now(), null, List.of("Danh mục không tìm thấy"))
                );
            }

            // Xóa ảnh cũ nếu có
            String imageUrl = existingCategoryNew.getImage_category_new();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                deleteOldImage(imageUrl);  // Xóa ảnh liên quan đến CategoryNew
            }

            // Xóa CategoryNew từ cơ sở dữ liệu
            categoryNewService.deleteById(id);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(
                    new ApiResponse<>("success", "Xóa danh mục thành công", LocalDateTime.now(), "Deleted ID: " + id, null)
            );
        } catch (Exception e) {
            // Trả về lỗi nếu có vấn đề xảy ra trong quá trình xóa
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi xóa danh mục", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }



    // =================== File Handling ===================

    /**
     * Kiểm tra file có phải là hình ảnh không
     */
    private boolean isImageFile(MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null && fileType.startsWith("image/");
    }

    /**
     * Lưu file ảnh vào thư mục uploads/categotyProduct và trả về đường dẫn tương đối
     */
    private String saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        // Đường dẫn tới thư mục lưu trữ ảnh
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "categoryNew";

        // Tạo thư mục nếu chưa tồn tại
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Lấy tên gốc và chuẩn hóa (bỏ khoảng trắng, ký tự đặc biệt)
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Tên file không hợp lệ");
        }

        // Thay thế khoảng trắng và ký tự đặc biệt bằng "_"
        originalFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-]", "_");

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        // Lưu file vào đường dẫn
        Path filePath = Paths.get(uploadDir, fileName);
        file.transferTo(filePath.toFile());

        // Trả về đường dẫn tương đối để lưu trong DB hoặc frontend sử dụng
        return "/uploads/categoryNew/" + fileName;
    }


    /**
     * Xóa file ảnh cũ khỏi thư mục uploads/categoryNew
     */
    private void deleteOldImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        // Thư mục chứa file ảnh
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "categoryNew";

        // Lấy đúng tên file từ imageUrl (tránh dư thư mục)
        String fileName = imageUrl.replace("/uploads/categoryNew/", "");

        // Ghép lại đường dẫn file thực tế
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
