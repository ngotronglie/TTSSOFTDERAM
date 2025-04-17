package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
        return product;
    }

    // Tạo sản phẩm mới
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        product.setCreated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        product.setUpdated_at(product.getCreated_at());
        return productService.save(product);
    }

    // Cập nhật sản phẩm theo ID
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
        existingProduct.setCategory_id_product(product.getCategory_id_product());
        existingProduct.setImage_varian_id(product.getImage_varian_id());
        existingProduct.setBranch_id(product.getBranch_id());
        existingProduct.setCity_id(product.getCity_id());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setImage(product.getImage());
        existingProduct.setStock(product.getStock());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBanner_show(product.getBanner_show());
        existingProduct.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        return productService.save(existingProduct);
    }

    // Xóa sản phẩm theo ID
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
