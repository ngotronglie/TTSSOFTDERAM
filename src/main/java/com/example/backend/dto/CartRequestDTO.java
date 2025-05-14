package com.example.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequestDTO {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}