package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.ImageBranch;
import com.example.backend.repository.ImageBranchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageBranchServiceImpl implements ImageBranchService {

    private final ImageBranchRepository imageBranchRepository;

    public ImageBranchServiceImpl(ImageBranchRepository imageBranchRepository) {
        this.imageBranchRepository = imageBranchRepository;
    }

    @Override
    public ApiResponse<List<ImageBranch>> findAll() {
        List<ImageBranch> images = imageBranchRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách ảnh chi nhánh thành công", LocalDateTime.now(), images, null);
    }

    @Override
    public ApiResponse<ImageBranch> findById(Long id) {
        ImageBranch image = imageBranchRepository.findById(id).orElse(null);
        if (image == null) {
            return new ApiResponse<>("error", "Không tìm thấy ảnh chi nhánh", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }
        return new ApiResponse<>("success", "Tìm ảnh thành công", LocalDateTime.now(), image, null);
    }

    @Override
    public ApiResponse<ImageBranch> save(ImageBranch imageBranch) {
        ImageBranch saved = imageBranchRepository.save(imageBranch);
        return new ApiResponse<>("success", "Thêm ảnh chi nhánh thành công", LocalDateTime.now(), saved, null);
    }

    @Override
    public ApiResponse<ImageBranch> update(Long id, ImageBranch updatedImage) {
        ImageBranch existing = imageBranchRepository.findById(id).orElse(null);
        if (existing == null) {
            return new ApiResponse<>("error", "Không tìm thấy ảnh để cập nhật", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }

        existing.setImage_branch(updatedImage.getImage_branch());
        existing.setIs_status(updatedImage.getIs_status());
        existing.setUpdated_at(LocalDateTime.now());

        imageBranchRepository.save(existing);
        return new ApiResponse<>("success", "Cập nhật ảnh chi nhánh thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        ImageBranch image = imageBranchRepository.findById(id).orElse(null);
        if (image == null) {
            return new ApiResponse<>("error", "Không tìm thấy ảnh để xóa", LocalDateTime.now(), null, List.of("Không tìm thấy ID: " + id));
        }

        imageBranchRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa ảnh thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
