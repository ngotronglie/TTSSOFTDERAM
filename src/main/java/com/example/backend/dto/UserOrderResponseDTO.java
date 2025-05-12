package com.example.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderResponseDTO {
    private Long orderId;
    private String code;
    private double totalPrice;
    private String status;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private List<OrderDetailDTO> orderDetails;
}

