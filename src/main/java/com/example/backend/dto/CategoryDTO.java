package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class CategoryDTO {
    private String imageCategory;
    private Map<String, CategoryChildDTO> categoryParentChild;
    private Map<String, ImageBranchDTO> imageBranch;
    private Map<String, ProductDTO> product;
}
