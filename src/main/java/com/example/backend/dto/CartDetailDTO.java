package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO {
    private Long idCart;
    private Integer userId;
    private Integer productId;
    private String image;
    private String name;
    private Integer quantity;
    private Float price;
} 