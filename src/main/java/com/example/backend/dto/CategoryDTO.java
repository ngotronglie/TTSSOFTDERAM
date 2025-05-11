package com.example.backend.dto;

import lombok.*;

import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {
    private String imageCategory;
    private Map<String, CategoryChildDTO> categoryParentChild;
    private Map<String, ImageBranchDTO> imageBranch;
    private Map<String, ProductDTO> product;
}
