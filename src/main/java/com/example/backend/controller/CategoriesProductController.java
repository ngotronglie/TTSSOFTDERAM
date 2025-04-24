package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryProduct;
import com.example.backend.service.CategoriesProductService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:9090"}) // Hạn chế CORS cho các origin cụ thể

@RestController
@RequestMapping("/api/categories-products")
public class CategoriesProductController {

    private final CategoriesProductService categoriesProductService;

    public CategoriesProductController(CategoriesProductService categoriesProductService) {
        this.categoriesProductService = categoriesProductService;
    }

    // =================== CRUD ===================

    @GetMapping
    public ApiResponse<List<CategoryProduct>> getAllCategoriesProducts() {
        return categoriesProductService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryProduct> getCategoriesProductById(@PathVariable Long id) {
        return categoriesProductService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CategoryProduct>> createCategoryProductWithImage(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile file,
            @RequestParam("parent_category_product_id") int parentCategoryProductId,
            @RequestParam("is_active") int isActive,
            HttpServletRequest request
    ) {
        // List lưu các lỗi nếu có
        List<String> errors = new ArrayList<>();

        // ===== VALIDATION =====

        // Kiểm tra tên không được rỗng
        if (name == null || name.trim().isEmpty()) {
            errors.add("Tên danh mục không được để trống");
        }

        // Kiểm tra parent_category_product_id hợp lệ
        if (parentCategoryProductId < 0) {
            errors.add("ID danh mục cha không hợp lệ");
        }

        // Kiểm tra is_active phải là 0 hoặc 1 (hoặc bất kỳ giá trị hợp lệ nào tùy thuộc vào yêu cầu)
        if (isActive < 0 || isActive > 1) {
            errors.add("Trạng thái phải là 0 hoặc 1");
        }

        // Kiểm tra file ảnh
        if (file == null || file.isEmpty()) {
            errors.add("Vui lòng chọn file ảnh");
        } else if (!file.getContentType().startsWith("image/")) {
            errors.add("Chỉ hỗ trợ file ảnh");
        }
        // Nếu có lỗi, trả về lỗi với thông tin chi tiết
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
            );
        }

        try {
            // ===== LƯU FILE =====
            String imageUrl = saveFile(file, request);

            // ===== TẠO CATEGORY PRODUCT =====
            CategoryProduct categoryProduct = new CategoryProduct();
            categoryProduct.setName(name);
            categoryProduct.setParent_category_product_id(parentCategoryProductId);
            categoryProduct.setIs_active(isActive);
            categoryProduct.setImage(imageUrl);
            categoryProduct.setCreated_at(LocalDateTime.now());
            categoryProduct.setUpdated_at(LocalDateTime.now());

            // Lưu CategoryProduct vào database
            ApiResponse<CategoryProduct> response = categoriesProductService.save(categoryProduct);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Trả về lỗi khi có sự cố trong quá trình lưu
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi tạo danh mục", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryProduct>> updateCategoryProductWithImage(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile file,
            @RequestParam("parent_category_product_id") int parentCategoryProductId,
            @RequestParam("is_active") int isActive,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            errors.add("Tên danh mục không được để trống");
        }

        if (parentCategoryProductId < 0) {
            errors.add("ID danh mục cha không hợp lệ");
        }

        if (isActive < 0 || isActive > 1) {
            errors.add("Trạng thái phải là 0 hoặc 1");
        }

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
            CategoryProduct existingCategoryProduct = categoriesProductService.findById(id).getData();
            if (existingCategoryProduct == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Danh mục không tồn tại", LocalDateTime.now(), null, List.of("Danh mục không tìm thấy"))
                );
            }

            System.out.println(">>> file :" + file);
            System.out.println(">>> existingCategoryProduct.getImage() :" + existingCategoryProduct.getImage());
            if (file != null && !file.isEmpty()) {
                deleteOldImage(existingCategoryProduct.getImage());
                String newImageUrl = saveFile(file, request);
                existingCategoryProduct.setImage(newImageUrl);
            }


            // Cập nhật dữ liệu
            existingCategoryProduct.setName(name);
            existingCategoryProduct.setParent_category_product_id(parentCategoryProductId);
            existingCategoryProduct.setIs_active(isActive);
            existingCategoryProduct.setUpdated_at(LocalDateTime.now());


            ApiResponse<CategoryProduct> response = categoriesProductService.update(id, existingCategoryProduct);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi hệ thống khi cập nhật danh mục", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategoryProduct(@PathVariable Long id) {
        try {
            // Kiểm tra xem CategoryProduct với id có tồn tại không
            CategoryProduct existingCategoryProduct = categoriesProductService.findById(id).getData();

            if (existingCategoryProduct == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Danh mục không tồn tại", LocalDateTime.now(), null, List.of("Danh mục không tìm thấy"))
                );
            }

            // Xóa ảnh cũ nếu có
            String imageUrl = existingCategoryProduct.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                deleteOldImage(imageUrl);  // Xóa ảnh liên quan đến CategoryProduct
            }

            // Xóa CategoryProduct từ cơ sở dữ liệu
            categoriesProductService.deleteById(id);

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
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "categoryProduct";

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
        return "/uploads/categoryProduct/" + fileName;
    }


    /**
     * Xóa file ảnh cũ khỏi thư mục uploads/categoryProduct
     */
    private void deleteOldImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        // Thư mục chứa file ảnh
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "categoryProduct";

        // Lấy đúng tên file từ imageUrl (tránh dư thư mục)
        String fileName = imageUrl.replace("/uploads/categoryProduct/", "");

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
