package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.City;
import com.example.backend.repository.CityReponsitory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityReponsitory cityRepository;

    public CityServiceImpl(CityReponsitory cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public ApiResponse<List<City>> findAll() {
        List<City> cities = cityRepository.findAll();
        return new ApiResponse<>("success", "Lấy danh sách thành phố thành công", LocalDateTime.now(), cities, null);
    }

    @Override
    public ApiResponse<City> findById(Long id) {
        City city = cityRepository.findById(id).orElse(null);

        if (city == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy thành phố với id: " + id);

            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy thành phố", LocalDateTime.now(), null, errorMessages);
        }

        return new ApiResponse<>("success", "Thành phố được tìm thấy", LocalDateTime.now(), city, null);
    }

    @Override
    public ApiResponse<City> save(City city) {
        City savedCity = cityRepository.save(city);
        return new ApiResponse<>("success", "Tạo thành phố thành công", LocalDateTime.now(), savedCity, null);
    }

    @Override
    public ApiResponse<City> update(Long id, City city) {
        City existing = cityRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy thành phố với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy thành phố", LocalDateTime.now(), null, errorMessages);
        }

        existing.setNameCity(city.getNameCity());
        cityRepository.save(existing);

        return new ApiResponse<>("success", "Cập nhật thành phố thành công", LocalDateTime.now(), existing, null);
    }

    @Override
    public ApiResponse<String> deleteById(Long id) {
        City existing = cityRepository.findById(id).orElse(null);
        if (existing == null) {
            // Tạo một danh sách lỗi thay vì chỉ một chuỗi lỗi
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Không tìm thấy thành phố với id: " + id);
            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>("error", "Không tìm thấy thành phố", LocalDateTime.now(), null, errorMessages);
        }
        cityRepository.deleteById(id);
        return new ApiResponse<>("success", "Xóa thành phố thành công", LocalDateTime.now(), "Deleted ID: " + id, null);
    }
}
