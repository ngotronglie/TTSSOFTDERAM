package com.example.backend.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long orderDetailId;
    private int productId;
    private String productName;
    private String productImage;
    private int quantity;
    private double price;
}
