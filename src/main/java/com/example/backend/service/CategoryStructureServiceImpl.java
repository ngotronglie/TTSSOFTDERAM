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

    private final CategoriesProductRepository categoriesProductRepository;
    private final ImageBranchRepository imageBranchRepository;
    private final ProductRepository productRepository;

    public CategoryStructureServiceImpl(
            CategoriesProductRepository categoriesProductRepository,
            ImageBranchRepository imageBranchRepository,
            ProductRepository productRepository) {
        this.categoriesProductRepository = categoriesProductRepository;
        this.imageBranchRepository = imageBranchRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ApiResponse<Map<String, CategoryResponseDTO>> getCategoryStructure() {
        try {
            // Get all parent categories (where parent_category_product_id is 0 or null)
            List<CategoryProduct> parentCategories = categoriesProductRepository.findAll().stream()
                    .filter(category -> category.getParent_category_product_id() == 0)
                    .collect(Collectors.toList());

            Map<String, CategoryResponseDTO> result = new HashMap<>();

            for (CategoryProduct parentCategory : parentCategories) {
                // Get child categories
                List<CategoryProduct> childCategories = categoriesProductRepository.findAll().stream()
                        .filter(category -> category.getParent_category_product_id() == parentCategory.getId_categories_product())
                        .collect(Collectors.toList());

                // Convert child categories to DTOs
                Map<String, CategoryChildDTO> childCategoryMap = new HashMap<>();
                for (int i = 0; i < childCategories.size(); i++) {
                    CategoryProduct child = childCategories.get(i);
                    childCategoryMap.put("category_child_" + (i + 1),
                            new CategoryChildDTO(child.getId_categories_product(), child.getName()));
                }

                // Get image branches
                List<ImageBranch> imageBranches = imageBranchRepository.findAll();
                Map<String, ImageBranchDTO> imageBranchMap = new HashMap<>();
                for (int i = 0; i < imageBranches.size(); i++) {
                    ImageBranch branch = imageBranches.get(i);
                    imageBranchMap.put("image_branch_" + (i + 1),
                            new ImageBranchDTO(branch.getId_image_branch(), branch.getImage_branch()));
                }

                // Get products for this category
                List<Product> products = productRepository.findAll().stream()
                        .filter(product -> product.getCategory_id_product() == parentCategory.getId_categories_product())
                        .collect(Collectors.toList());

                Map<String, ProductDTO> productMap = new HashMap<>();
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    productMap.put("product " + (i + 1),
                            new ProductDTO(
                                    String.valueOf(product.getId_product()),
                                    product.getName(),
                                    product.getImage(),
                                    product.getPrice()
                            ));
                }

                // Create the category response
                CategoryResponseDTO categoryResponse = new CategoryResponseDTO(
                        parentCategory.getImage(),
                        childCategoryMap,
                        imageBranchMap,
                        productMap
                );

                result.put("category " + parentCategory.getId_categories_product(), categoryResponse);
            }

            return new ApiResponse<>("success", "Category structure retrieved successfully", LocalDateTime.now(), result, null);
        } catch (Exception e) {
            return new ApiResponse<>("error", "Failed to retrieve category structure", LocalDateTime.now(), null,
                    Collections.singletonList(e.getMessage()));
        }
    }
} 