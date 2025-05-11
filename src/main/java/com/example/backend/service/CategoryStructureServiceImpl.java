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
            // Lấy tất cả các danh mục cha (where parent_category_product_id is 0 hoặc null)
            List<CategoryProduct> parentCategories = categoriesProductRepository.findAll().stream()
                    // Kiểm tra nếu parent_category_product_id = 0 hoặc null (danh mục cha)
                    .filter(category -> category.getParent_category_product_id() == null || category.getParent_category_product_id() == 0)
                    .collect(Collectors.toList());

            // Khởi tạo một Map để chứa kết quả
            Map<String, CategoryResponseDTO> result = new HashMap<>();

            // Lặp qua từng danh mục cha để lấy thông tin liên quan
            for (CategoryProduct parentCategory : parentCategories) {
                // Lấy các danh mục con của danh mục cha hiện tại
                List<CategoryProduct> childCategories = categoriesProductRepository.findAll().stream()
                        // Kiểm tra nếu parent_category_product_id của danh mục con bằng id của danh mục cha
                        .filter(category -> category.getParent_category_product_id() != null && 
                                category.getParent_category_product_id() == parentCategory.getId_categories_product())
                        .collect(Collectors.toList());

                // Chuyển các danh mục con thành DTO để trả về cho client
                Map<String, CategoryChildDTO> childCategoryMap = new HashMap<>();
                for (int i = 0; i < childCategories.size(); i++) {
                    CategoryProduct child = childCategories.get(i);
                    // Lưu vào Map với key là "category_child_" + chỉ số
                    childCategoryMap.put("category_child_" + (i + 1),
                            new CategoryChildDTO(child.getId_categories_product(), child.getName()));
                }

                // Lấy tất cả hình ảnh chi nhánh (image branches)
                List<ImageBranch> imageBranches = imageBranchRepository.findAll();
                List<ImageBranchDTO> imageBranchList = imageBranches.stream()
                        .map(branch -> new ImageBranchDTO(branch.getId_image_branch(), branch.getImage_branch()))
                        .collect(Collectors.toList());

                // Lấy tất cả sản phẩm của danh mục cha và con
                Set<Long> allCategoryIds = new HashSet<>();
                allCategoryIds.add(parentCategory.getId_categories_product().longValue());
                allCategoryIds.addAll(childCategories.stream()
                        .map(CategoryProduct::getId_categories_product)
                        .map(Long::valueOf)
                        .collect(Collectors.toSet()));

                List<Product> products = productRepository.findAll().stream()
                        .filter(product -> product.getCategory_id_product() != null && 
                                allCategoryIds.contains(product.getCategory_id_product().longValue()))
                        .collect(Collectors.toList());

                // Chuyển các sản phẩm thành DTO và lưu vào Map
                Map<String, ProductDTO> productMap = new HashMap<>();
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    // Lưu vào Map với key là "product" + chỉ số
                    productMap.put("product " + (i + 1),
                            new ProductDTO(
                                    String.valueOf(product.getId_product()),  // Chuyển ID sản phẩm thành chuỗi
                                    product.getName(),                      // Tên sản phẩm
                                    product.getImage(),                     // Hình ảnh sản phẩm
                                    product.getPrice()                      // Giá sản phẩm
                            ));
                }


                // Tạo một CategoryResponseDTO từ các dữ liệu đã lấy được
                CategoryResponseDTO categoryResponse = new CategoryResponseDTO(
                        parentCategory.getImage(),           // Hình ảnh của danh mục cha
                        childCategoryMap,                    // Các danh mục con
                        imageBranchList,                     // Các hình ảnh chi nhánh
                        productMap                           // Các sản phẩm của danh mục cha
                );

                // Sử dụng tên category làm key thay vì "category + id"
                result.put(parentCategory.getName(), categoryResponse);
            }

            // Trả về kết quả API thành công
            return new ApiResponse<>("success", "Category structure retrieved successfully", LocalDateTime.now(), result, null);
        } catch (Exception e) {
            // Nếu có lỗi, trả về kết quả API thất bại với thông tin lỗi
            return new ApiResponse<>("error", "Failed to retrieve category structure", LocalDateTime.now(), null,
                    Collections.singletonList(e.getMessage()));
        }
    }
}
