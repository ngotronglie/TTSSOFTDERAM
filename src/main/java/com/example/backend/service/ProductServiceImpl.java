package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ApiResponse<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách sản phẩm thành công", LocalDateTime.now(), products, null);
    }

    @Override
    public ApiResponse<Product> findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new ApiResponse<>("error", "Không tìm thấy sản phẩm", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }
        return new ApiResponse<>("success", "Tìm sản phẩm thành công", LocalDateTime.now(), product, null);
    }

    @Override
    public ApiResponse<Product> save(Product product) {
        product.setCreated_at(LocalDateTime.now());
        product.setUpdated_at(LocalDateTime.now());
        Product saved = productRepository.save(product);
        return new ApiResponse<>("success", "Thêm sản phẩm thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<Product> update(Long id, Product product) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy sản phẩm", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setDiscount(product.getDiscount());
        existing.setStock(product.getStock());
        existing.setCategory_id_product(product.getCategory_id_product());
        existing.setImage_varian_id(product.getImage_varian_id());
        existing.setBranch_id(product.getBranch_id());
        existing.setCity_id(product.getCity_id());
        existing.setImage(product.getImage());
        existing.setDescription(product.getDescription());
        existing.setBanner_show(product.getBanner_show());
        existing.setUpdated_at(LocalDateTime.now());

        Product saved = productRepository.save(existing);
        return new ApiResponse<>("success", "Cập nhật sản phẩm thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new ApiResponse<>("error", "Không tìm thấy sản phẩm", LocalDateTime.now(), null, List.of("ID không tồn tại: " + id));
        }

        productRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa sản phẩm thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
