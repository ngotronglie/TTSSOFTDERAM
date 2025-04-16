package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.City;
import com.example.backend.dto.ApiResponse;
import com.example.backend.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<City>>> getAllCities() {
        List<City> cities = cityService.findAll();
        ApiResponse<List<City>> response = new ApiResponse<>(
                "success",
                "Lấy danh sách thành phố thành công",
                LocalDateTime.now(),
                cities,
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<City>> getCityById(@PathVariable Long id) {
        City city = cityService.findById(id);
        if (city == null) {
            throw new EntityNotFoundException("Không tìm thấy thành phố với ID: " + id);
        }

        ApiResponse<City> response = new ApiResponse<>(
                "success",
                "Lấy thành phố thành công",
                LocalDateTime.now(),
                city,
                null
        );
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<City>> createCity(@RequestBody City city) {
        City createdCity = cityService.save(city);
        ApiResponse<City> response = new ApiResponse<>(
                "success",
                "Tạo thành phố thành công",
                LocalDateTime.now(),
                createdCity,
                null
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<City>> updateCity(@PathVariable Long id, @RequestBody City city) {
        City existingCity = cityService.findById(id);
        if (existingCity == null) {
            throw new EntityNotFoundException("City with id " + id + " not found");
        }

        existingCity.setNameCity(city.getNameCity());
        City updatedCity = cityService.save(existingCity);

        ApiResponse<City> response = new ApiResponse<>(
                "success",
                "Cập nhật thành phố thành công",
                LocalDateTime.now(),
                updatedCity,
                null
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCity(@PathVariable Long id) {
        cityService.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(
                "success",
                "Xóa thành phố thành công",
                LocalDateTime.now(),
                null,
                null
        );
        return ResponseEntity.ok(response);
    }
}
