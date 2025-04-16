package com.example.backend.service;

import com.example.backend.entity.ImageVarian;
import com.example.backend.repository.ImageVarianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageVarianService {

    private final ImageVarianRepository imageVarianRepository;

    public ImageVarianService(ImageVarianRepository imageVarianRepository) {
        this.imageVarianRepository = imageVarianRepository;
    }

    public List<ImageVarian> findAll() {
        return imageVarianRepository.findAll();
    }

    public ImageVarian findById(Long id) {
        return imageVarianRepository.findById(id).orElse(null);
    }

    public ImageVarian save(ImageVarian imageVarian) {
        return imageVarianRepository.save(imageVarian);
    }

    public void deleteById(Long id) {
        imageVarianRepository.deleteById(id);
    }
}