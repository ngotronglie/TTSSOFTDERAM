package com.example.backend.controller;
import com.example.backend.entity.ImageVarian;
import com.example.backend.service.ImageVarianServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/image-varian")
public class ImageVarianController {

    private final ImageVarianServiceImpl imageVarianServiceImpl;

    public ImageVarianController(ImageVarianServiceImpl imageVarianServiceImpl) {
        this.imageVarianServiceImpl = imageVarianServiceImpl;
    }

    // Lấy tất cả ImageVarian
    @GetMapping
    public List<ImageVarian> getAllImageVarians() {
        return imageVarianServiceImpl.findAll();
    }

    // Lấy ImageVarian theo ID
    @GetMapping("/{id}")
    public ImageVarian getImageVarianById(@PathVariable Long id) {
        ImageVarian imageVarian = imageVarianServiceImpl.findById(id);
        if (imageVarian == null) {
            throw new EntityNotFoundException("ImageVarian with id " + id + " not found");
        }
        return imageVarian;
    }

    // Tạo mới một ImageVarian
    @PostMapping
    public ImageVarian createImageVarian(@RequestBody ImageVarian imageVarian) {
        imageVarian.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return imageVarianServiceImpl.save(imageVarian);
    }

    // Cập nhật thông tin của một ImageVarian
    @PutMapping("/{id}")
    public ImageVarian updateImageVarian(@PathVariable Long id, @RequestBody ImageVarian imageVarian) {
        ImageVarian existing = imageVarianServiceImpl.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException("ImageVarian with id " + id + " not found");
        }
        existing.setImage_url(imageVarian.getImage_url());
//        existing.setCreated_at(imageVarian.getCreated_at());
        existing.setUpdated_at(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        return imageVarianServiceImpl.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteImageVarian(@PathVariable Long id) {
        imageVarianServiceImpl.deleteById(id);
    }
}
