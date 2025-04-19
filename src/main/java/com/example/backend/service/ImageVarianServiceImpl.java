package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.ImageVarian;
import com.example.backend.repository.ImageVarianRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageVarianServiceImpl implements ImageVarianService {

    private final ImageVarianRepository imageVarianRepository;

    public ImageVarianServiceImpl(ImageVarianRepository imageVarianRepository) {
        this.imageVarianRepository = imageVarianRepository;
    }

    @Override
    public ApiResponse<List<ImageVarian>> findAll() {
        return new ApiResponse<>("success", "Lấy danh sách ảnh biến thể thành công", LocalDateTime.now(),
                imageVarianRepository.findAll(), null);
    }

    @Override
    public ApiResponse<ImageVarian> findById(Long id) {
        ImageVarian image = imageVarianRepository.findById(id).orElse(null);
        if (image == null) {
            return new ApiResponse<>("error", "Không tìm thấy ảnh", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }
        return new ApiResponse<>("success", "Tìm ảnh thành công", LocalDateTime.now(), image, null);
    }

    @Override
    public ApiResponse<ImageVarian> save(ImageVarian imageVarian) {
        imageVarian.setCreated_at(LocalDateTime.now());
        imageVarian.setUpdated_at(LocalDateTime.now());
        ImageVarian saved = imageVarianRepository.save(imageVarian);
        return new ApiResponse<>("success", "Thêm ảnh biến thể thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<ImageVarian> update(Long id, ImageVarian updatedImage) {
        ImageVarian existing = imageVarianRepository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy ảnh", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }

        existing.setImage_url(updatedImage.getImage_url());
        existing.setUpdated_at(LocalDateTime.now());
        imageVarianRepository.save(existing);
        return new ApiResponse<>("success", "Cập nhật ảnh thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        ImageVarian image = imageVarianRepository.findById(id).orElse(null);
        if (image == null) {
            return new ApiResponse<>("error", "Không tìm thấy ảnh để xóa", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }

        imageVarianRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa ảnh thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
