package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Product;
import com.example.backend.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // =================== CRUD ===================

    @GetMapping
    public ApiResponse<List<Product>> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> createProductWithImage(
            @RequestParam("name") String name,
            @RequestParam("price") float price,
            @RequestParam("discount") float discount,
            @RequestParam("description") String description,
            @RequestParam("stock") int stock,
            @RequestParam("branch_id") int branchId,
            @RequestParam("category_id_product") int categoryIdProduct,
            @RequestParam("city_id") int cityId,
            @RequestParam("image_varian_id") int imageVarianId,
            @RequestParam("banner_show") int bannerShow,
            @RequestParam("image") MultipartFile file,
            HttpServletRequest request
    ) {
        List<String> errors = validateProductFields(name, price, file, discount, stock, description);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
            );
        }
        try {
            String imagePath = saveFile(file, request);

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDiscount(discount);
            product.setDescription(description);
            product.setStock(stock);
            product.setBranch_id(branchId);
            product.setCategory_id_product(categoryIdProduct);
            product.setCity_id(cityId);
            product.setImage_varian_id(imageVarianId);
            product.setBanner_show(bannerShow);
            product.setImage(imagePath);
            product.setCreated_at(LocalDateTime.now());
            product.setUpdated_at(LocalDateTime.now());

            ApiResponse<Product> response = productService.save(product);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi tạo sản phẩm", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProductWithImage(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") float price,
            @RequestParam("discount") float discount,
            @RequestParam("description") String description,
            @RequestParam("stock") int stock,
            @RequestParam("branch_id") int branchId,
            @RequestParam("category_id_product") int categoryIdProduct,
            @RequestParam("city_id") int cityId,
            @RequestParam("image_varian_id") int imageVarianId,
            @RequestParam("banner_show") int bannerShow,
            @RequestParam(value = "image", required = false) MultipartFile file,
            HttpServletRequest request
    ) {
        List<String> errors = validateProductFields(name, price, file,discount, stock, description);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("error", "Dữ liệu không hợp lệ", LocalDateTime.now(), null, errors)
            );
        }

        try {
            Product existingProduct = productService.findById(id).getData();
            if (existingProduct == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Không tìm thấy sản phẩm", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id))
                );
            }

            // Nếu có file ảnh mới thì xóa ảnh cũ và lưu ảnh mới
            if (file != null && !file.isEmpty()) {
                deleteOldImage(existingProduct.getImage());
                String newImageUrl = saveFile(file, request);
                existingProduct.setImage(newImageUrl);
            }

            existingProduct.setName(name);
            existingProduct.setPrice(price);
            existingProduct.setDiscount(discount);
            existingProduct.setDescription(description);
            existingProduct.setStock(stock);
            existingProduct.setBranch_id(branchId);
            existingProduct.setCategory_id_product(categoryIdProduct);
            existingProduct.setCity_id(cityId);
            existingProduct.setImage_varian_id(imageVarianId);
            existingProduct.setBanner_show(bannerShow);
            existingProduct.setUpdated_at(LocalDateTime.now());

            ApiResponse<Product> response = productService.update(id, existingProduct);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi cập nhật sản phẩm", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        try {
            Product product = productService.findById(id).getData();
            if (product == null) {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Không tìm thấy sản phẩm", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id))
                );
            }

            deleteOldImage(product.getImage());
            productService.deleteById(id);

            return ResponseEntity.ok(
                    new ApiResponse<>("success", "Xóa sản phẩm thành công", LocalDateTime.now(), "Deleted ID: " + id, null)
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>("error", "Lỗi khi xóa sản phẩm", LocalDateTime.now(), null, List.of(e.getMessage()))
            );
        }
    }

    // =================== Helper methods ===================

    private List<String> validateProductFields(String name, Float price, MultipartFile file, Float discount, Integer stock, String Description)
    {
    List<String> errors = new ArrayList<>();


        if (file != null && !file.getContentType().startsWith("image/")) errors.add("File tải lên phải là ảnh");
        if (name == null || name.trim().isEmpty()) {
            errors.add("Tên sản phẩm không được để trống");
        }

        if (price == null) {
            errors.add("Giá sản phẩm không được để trống");
        } else if (price < 0) {
            errors.add("Giá sản phẩm không hợp lệ");
        }

        if (file == null || file.isEmpty()) {
            errors.add("Vui lòng chọn ảnh sản phẩm");
        } else if (!file.getContentType().startsWith("image/")) {
            errors.add("Tệp tải lên phải là hình ảnh");
        }

        if (discount == null) {
            errors.add("Giá giảm không được để trống");
        } else if (discount < 0) {
            errors.add("Giá giảm không hợp lệ");
        }

        if (stock == null) {
            errors.add("Số lượng không được để trống");
        } else if (stock < 0) {
            errors.add("Số lượng sản phẩm không hợp lệ");
        }
        if (Description == null || Description.trim().isEmpty()) {
            errors.add("chi tiet san pham khong de trong!!");
        }

        return errors;
    }

    private String saveFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "product";

        File directory = new File(uploadDir);
        if (!directory.exists()) directory.mkdirs();

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new IOException("Tên file không hợp lệ");

        originalFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-]", "_");
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        Path filePath = Paths.get(uploadDir, fileName);
        file.transferTo(filePath.toFile());

        return "/uploads/product/" + fileName;
    }

    private void deleteOldImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "product";
        String fileName = imageUrl.replace("/uploads/product/", "");
        String filePath = uploadDir + File.separator + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✅ Xóa file ảnh cũ: " + filePath);
            } else {
                System.out.println("❌ Không thể xóa file ảnh: " + filePath);
            }
        }
    }

}
