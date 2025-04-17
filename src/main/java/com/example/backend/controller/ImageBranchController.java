package com.example.backend.controller;

import com.example.backend.entity.ImageBranch;
import com.example.backend.service.ImageBranchServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/image-branch")
public class ImageBranchController {

    private final ImageBranchServiceImpl imageBranchServiceImpl;

    public ImageBranchController(ImageBranchServiceImpl imageBranchServiceImpl) {
        this.imageBranchServiceImpl = imageBranchServiceImpl;
    }

    @GetMapping
    public List<ImageBranch> getAllImageBranches() {
        return imageBranchServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ImageBranch getImageBranchById(@PathVariable Long id) {
        ImageBranch imageBranch = imageBranchServiceImpl.findById(id);
        if (imageBranch == null) {
            throw new EntityNotFoundException("ImageBranch with id " + id + " not found");
        }
        return imageBranch;
    }

    @PostMapping
    public ImageBranch createImageBranch(@RequestBody ImageBranch imageBranch) {
        imageBranch.setCreated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        imageBranch.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return imageBranchServiceImpl.save(imageBranch);
    }

    @PutMapping("/{id}")
    public ImageBranch updateImageBranch(@PathVariable Long id, @RequestBody ImageBranch imageBranch) {
        ImageBranch existing = imageBranchServiceImpl.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException("ImageBranch with id " + id + " not found");
        }
        existing.setImage_branch(imageBranch.getImage_branch());
        existing.setIs_status(imageBranch.getIs_status());
        existing.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return imageBranchServiceImpl.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteImageBranch(@PathVariable Long id) {
        imageBranchServiceImpl.deleteById(id);
    }
}
