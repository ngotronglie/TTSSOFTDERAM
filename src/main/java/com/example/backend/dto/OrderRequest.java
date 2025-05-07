package com.example.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Integer userId;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private String paymentMethod;
    private Double totalPrice;
    private List<OrderDetailRequest> orderDetails;
}

