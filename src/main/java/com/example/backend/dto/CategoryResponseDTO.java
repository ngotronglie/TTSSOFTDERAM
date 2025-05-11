package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    @JsonProperty("image_category")
    private String imageCategory;
    
    @JsonProperty("category_parent_child")
    private Map<String, CategoryChildDTO> categoryParentChild;
    
    @JsonProperty("image_branch")
    private List<ImageBranchDTO> imageBranch;
    
    @JsonProperty("product")
    private Map<String, ProductDTO> product;
} 