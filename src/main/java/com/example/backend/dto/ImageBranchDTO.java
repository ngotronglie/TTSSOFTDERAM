package com.example.backend.dto;

public class ImageBranchDTO {
    private Long image_branch_id;
    private String image_branch;

    public ImageBranchDTO(Long image_branch_id, String image_branch) {
        this.image_branch_id = image_branch_id;
        this.image_branch = image_branch;
    }

    public Long getImage_branch_id() {
        return image_branch_id;
    }

    public void setImage_branch_id(Long image_branch_id) {
        this.image_branch_id = image_branch_id;
    }

    public String getImage_branch() {
        return image_branch;
    }

    public void setImage_branch(String image_branch) {
        this.image_branch = image_branch;
    }
}
