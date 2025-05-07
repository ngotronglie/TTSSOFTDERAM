package com.example.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailRequest {
    private Integer productId;
    private Integer quantity;
    private Double price;
}
