package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_product")
    @SequenceGenerator(name = "id_product", sequenceName = "PRODUCT_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_product;

    @Column(name = "category_id_product")
    private Integer category_id_product;

    @Column(name = "image_varian_id")
    private Integer image_varian_id;

    @Column(name = "branch_id")
    private Integer branch_id;

    @Column(name = "city_id")
    private Integer city_id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "image") // cai nay la anh
    private String image;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "description")
    @Lob
    private String description;


    @Column(name = "banner_show")
    private Integer banner_show;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Integer getBanner_show() {
        return banner_show;
    }

    public void setBanner_show(Integer banner_show) {
        this.banner_show = banner_show;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getCategory_id_product() {
        return category_id_product;
    }

    public void setCategory_id_product(Integer category_id_product) {
        this.category_id_product = category_id_product;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getImage_varian_id() {
        return image_varian_id;
    }

    public void setImage_varian_id(Integer image_varian_id) {
        this.image_varian_id = image_varian_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }




}
