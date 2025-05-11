package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "categories_product")
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_category_product")
    @SequenceGenerator(name = "id_category_product", sequenceName = "CATEGORY_PRODUCT_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Integer id_categories_product;
    @Column(name = "name")
    @NotEmpty(message = "ten danh muc khong duoc de trong!")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "parent_category_product_id")
    private Integer parent_category_product_id;

    @Column(name = "is_active")
    private int is_active;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;


}
