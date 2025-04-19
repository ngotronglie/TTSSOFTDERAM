package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.CategoryNew;
import com.example.backend.repository.CategoryNewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryNewServiceImpl implements CategoryNewService {

    private final CategoryNewRepository categoryNewRepository;

    public CategoryNewServiceImpl(CategoryNewRepository categoryNewRepository) {
        this.categoryNewRepository = categoryNewRepository;
    }

    @Override
    public ApiResponse<List<CategoryNew>> findAll() {
        List<CategoryNew> categories = categoryNewRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách danh mục tin tức thành công", LocalDateTime.now(), categories, null);
    }

    @Override
    public ApiResponse<CategoryNew> findById(Long id) {
        CategoryNew category = categoryNewRepository.findById(id).orElse(null);

        if (category == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Không tìm thấy danh mục tin tức với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy danh mục", LocalDateTime.now(), null, errors);
        }

        return new ApiResponse<>("success", "Danh mục tin tức được tìm thấy", LocalDateTime.now(), category, null);
    }

    @Override
    public ApiResponse<CategoryNew> save(CategoryNew categoryNew) {
        CategoryNew saved = categoryNewRepository.save(categoryNew);
        LocalDateTime now = LocalDateTime.now();
        categoryNew.setCreated_at(now);
        categoryNew.setUpdated_at(now);
        return new ApiResponse<>("success", "Tạo danh mục tin tức thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<CategoryNew> update(Long id, CategoryNew updatedCategory) {
        CategoryNew existing = categoryNewRepository.findById(id).orElse(null);

        if (existing == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Không tìm thấy danh mục với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy danh mục", LocalDateTime.now(), null, errors);
        }

        existing.setName_category_new(updatedCategory.getName_category_new());
        existing.setImage_category_new(updatedCategory.getImage_category_new());
        existing.setParent_category_new_id(updatedCategory.getParent_category_new_id());
        existing.setIs_active(updatedCategory.getIs_active());
        existing.setUpdated_at(LocalDateTime.now());
        existing.setCreated_at(updatedCategory.getCreated_at());

        categoryNewRepository.save(existing);

        return new ApiResponse<>("success", "Cập nhật danh mục tin tức thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        CategoryNew existing = categoryNewRepository.findById(id).orElse(null);

        if (existing == null) {
            List<String> errors = new ArrayList<>();
            errors.add("Không tìm thấy danh mục với id: " + id);
            return new ApiResponse<>("error", "Không tìm thấy danh mục", LocalDateTime.now(), null, errors);
        }

        categoryNewRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa danh mục tin tức thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
