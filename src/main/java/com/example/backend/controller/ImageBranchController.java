package com.example.backend.controller;

import com.example.backend.entity.ImageBranch;
import com.example.backend.service.ImageBranchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/image-branch")
public class ImageBranchController {

    private final ImageBranchService imageBranchService;

    public ImageBranchController(ImageBranchService imageBranchService) {
        this.imageBranchService = imageBranchService;
    }

    @GetMapping
    public List<ImageBranch> getAllImageBranches() {
        return imageBranchService.findAll();
    }

    @GetMapping("/{id}")
    public ImageBranch getImageBranchById(@PathVariable Long id) {
        ImageBranch imageBranch = imageBranchService.findById(id);
        if (imageBranch == null) {
            throw new EntityNotFoundException("ImageBranch with id " + id + " not found");
        }
        return imageBranch;
    }

    @PostMapping
    public ImageBranch createImageBranch(@RequestBody ImageBranch imageBranch) {
        imageBranch.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return imageBranchService.save(imageBranch);
    }

    @PutMapping("/{id}")
    public ImageBranch updateImageBranch(@PathVariable Long id, @RequestBody ImageBranch imageBranch) {
        ImageBranch existing = imageBranchService.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException("ImageBranch with id " + id + " not found");
        }
        existing.setImage_branch(imageBranch.getImage_branch());
        existing.setIs_status(imageBranch.getIs_status());
        existing.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return imageBranchService.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteImageBranch(@PathVariable Long id) {
        imageBranchService.deleteById(id);
    }
}
