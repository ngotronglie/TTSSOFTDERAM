package com.example.backend.service;

import com.example.backend.entity.ImageBranch;
import com.example.backend.repository.ImageBranchRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ImageBranchService {
    private final ImageBranchRepository imageBranchRepository;

    public ImageBranchService(ImageBranchRepository imageBranchRepository) {
        this.imageBranchRepository = imageBranchRepository;
    }

    public List<ImageBranch> findAll() {
        return imageBranchRepository.findAll();
    }

    public ImageBranch findById(Long id) {
        return imageBranchRepository.findById(id).orElse(null);
    }

    public ImageBranch save(ImageBranch imageBranch) {
        return imageBranchRepository.save(imageBranch);
    }

    public void deleteById(Long id) {
        imageBranchRepository.deleteById(id);
    }
}
