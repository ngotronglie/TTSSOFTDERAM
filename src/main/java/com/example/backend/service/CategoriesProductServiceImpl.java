package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryProduct;
import com.example.backend.repository.CategoriesProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesProductServiceImpl implements CategoriesProductService {

    private final CategoriesProductRepository categoriesProductRepository;

    public CategoriesProductServiceImpl(CategoriesProductRepository categoriesProductRepository) {
        this.categoriesProductRepository = categoriesProductRepository;
    }

    @Override
    public ApiResponse<List<CategoryProduct>> findAll() {
        List<CategoryProduct> categories = categoriesProductRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách danh mục thành công", LocalDateTime.now(), categories, null);
    }

    @Override
    public ApiResponse<CategoryProduct> findById(Long id) {
        CategoryProduct category = categoriesProductRepository.findById(id).orElse(null);

        if (category == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy danh mục với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy danh mục", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Danh mục được tìm thấy", LocalDateTime.now(), category, null);
    }

    @Override
    public ApiResponse<CategoryProduct> save(CategoryProduct category) {
        CategoryProduct saved = categoriesProductRepository.save(category);
        return new ApiResponse<>("success", "Tạo danh mục thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<CategoryProduct> update(Long id, CategoryProduct updatedCategory) {
        // Tìm CategoryProduct tồn tại trong cơ sở dữ liệu
        CategoryProduct existing = categoriesProductRepository.findById(id).orElse(null);

        if (existing == null) {
            // Nếu không tìm thấy danh mục, trả về lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy danh mục với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy danh mục", LocalDateTime.now(), null, errorMessages);
        }

        // Cập nhật các trường dữ liệu của danh mục hiện tại với giá trị mới
        existing.setName(updatedCategory.getName());
        existing.setImage(updatedCategory.getImage());
        existing.setParent_category_product_id(updatedCategory.getParent_category_product_id());
        existing.setIs_active(updatedCategory.getIs_active());
        existing.setUpdated_at(LocalDateTime.now());

        // Lưu lại danh mục đã cập nhật vào cơ sở dữ liệu
        categoriesProductRepository.save(existing); // Sử dụng save thay vì update

        // Trả về phản hồi thành công
        return new ApiResponse<>("success", "Cập nhật danh mục thành công", LocalDateTime.now(), existing, null);
    }


    @Override
    public ApiResponse<String> deleteById(Long id) {
        CategoryProduct existing = categoriesProductRepository.findById(id).orElse(null);

        if (existing == null) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy danh mục với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy danh mục", LocalDateTime.now(), null, errorMessages);
        }

        categoriesProductRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa danh mục thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
