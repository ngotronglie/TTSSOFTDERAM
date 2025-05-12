package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.CategoryProduct;
import com.example.backend.entity.ImageBranch;
import com.example.backend.entity.Product;
import com.example.backend.repository.CategoriesProductRepository;
import com.example.backend.repository.ImageBranchRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryStructureServiceImpl implements CategoryStructureService {

    // Khai báo các repository để truy cập cơ sở dữ liệu
    private final CategoriesProductRepository categoriesProductRepository;
    private final ImageBranchRepository imageBranchRepository;
    private final ProductRepository productRepository;

    // Constructor để khởi tạo các repository
    public CategoryStructureServiceImpl(
            CategoriesProductRepository categoriesProductRepository,
            ImageBranchRepository imageBranchRepository,
            ProductRepository productRepository) {
        this.categoriesProductRepository = categoriesProductRepository;
        this.imageBranchRepository = imageBranchRepository;
        this.productRepository = productRepository;
    }

    // Phương thức để lấy cấu trúc danh mục (category structure)
    @Override
    public ApiResponse<Map<String, CategoryResponseDTO>> getCategoryStructure() {
        try {
            // Lấy tất cả các danh mục
            List<CategoryProduct> allCategories = categoriesProductRepository.findAll();
            
            // Lấy các danh mục cha (parent_category_product_id là null hoặc 0)
            List<CategoryProduct> parentCategories = allCategories.stream()
                    .filter(category -> category.getParent_category_product_id() == null || 
                            category.getParent_category_product_id() == 0)
                    .collect(Collectors.toList());

            Map<String, CategoryResponseDTO> result = new HashMap<>();

            for (CategoryProduct parentCategory : parentCategories) {
                // Lấy các danh mục con của danh mục cha hiện tại
                List<CategoryProduct> childCategories = allCategories.stream()
                        .filter(category -> category.getParent_category_product_id() != null && 
                                category.getParent_category_product_id().equals(parentCategory.getId_categories_product()))
                        .collect(Collectors.toList());

                // Tạo map cho danh mục con
                Map<String, CategoryChildDTO> childCategoryMap = new HashMap<>();
                for (CategoryProduct child : childCategories) {
                    childCategoryMap.put(child.getName(), 
                            new CategoryChildDTO(child.getId_categories_product(), child.getName()));
                }

                // Lấy tất cả hình ảnh chi nhánh
                List<ImageBranch> imageBranches = imageBranchRepository.findAll();
                List<ImageBranchDTO> imageBranchList = imageBranches.stream()
                        .map(branch -> new ImageBranchDTO(branch.getId_image_branch(), branch.getImage_branch()))
                        .collect(Collectors.toList());

                // Tạo set chứa ID của danh mục cha và tất cả danh mục con
                Set<Integer> allCategoryIds = new HashSet<>();
                allCategoryIds.add(parentCategory.getId_categories_product());
                allCategoryIds.addAll(childCategories.stream()
                        .map(CategoryProduct::getId_categories_product)
                        .collect(Collectors.toSet()));

                // Lấy tất cả sản phẩm thuộc danh mục cha và các danh mục con
                List<Product> products = productRepository.findAll().stream()
                        .filter(product -> allCategoryIds.contains(product.getCategory_id_product()))
                        .collect(Collectors.toList());

                // Tạo map cho sản phẩm
                Map<String, ProductDTO> productMap = new HashMap<>();
                for (Product product : products) {
                    productMap.put(product.getName(),
                            new ProductDTO(
                                    String.valueOf(product.getId_product()),
                                    product.getName(),
                                    product.getImage(),
                                    product.getPrice()
                            ));
                }

                // Tạo CategoryResponseDTO
                CategoryResponseDTO categoryResponse = new CategoryResponseDTO(
                        parentCategory.getImage(),
                        childCategoryMap,
                        imageBranchList,
                        productMap
                );

                result.put(parentCategory.getName(), categoryResponse);
            }

            // Trả về kết quả API thành công
            return new ApiResponse<>("success", "Category structure retrieved successfully", 
                    LocalDateTime.now(), result, null);
        } catch (Exception e) {
            // Nếu có lỗi, trả về kết quả API thất bại với thông tin lỗi
            return new ApiResponse<>("error", "Failed to retrieve category structure", 
                    LocalDateTime.now(), null, Collections.singletonList(e.getMessage()));
        }
    }
}
