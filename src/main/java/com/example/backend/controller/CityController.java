package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.City;
import com.example.backend.service.CityService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ApiResponse<List<City>> getAllCities() {
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public ApiResponse<City> getCityById(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @PostMapping
    public ApiResponse<City> createCity(@Valid @RequestBody City city, BindingResult bindingResult) {
        // Kiểm tra xem có lỗi nào trong quá trình validate không
        if (bindingResult.hasErrors()) {
            // Dùng stream để lấy tất cả các thông báo lỗi và gom lại thành một danh sách
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage) // Lấy thông báo lỗi của mỗi trường
                    .collect(Collectors.toList());

            // Trả về ApiResponse với danh sách lỗi
            return new ApiResponse<>(
                    "error",
                    "Dữ liệu không hợp lệ",
                    LocalDateTime.now(),
                    null,
                    errorMessages // Trả về danh sách lỗi
            );
        }

        // Nếu không có lỗi, gọi phương thức save để lưu thành phố
        return cityService.save(city);
    }


    @PutMapping("/{id}")
    public ApiResponse<City> updateCity(@PathVariable Long id,
                                        @Valid @RequestBody City city,
                                        BindingResult bindingResult) {
        // Kiểm tra lỗi validation
        if (bindingResult.hasErrors()) {
            // Lấy tất cả các lỗi và trả về chúng dưới dạng mảng
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ApiResponse<>(
                    "error",
                    "Dữ liệu không hợp lệ",
                    LocalDateTime.now(),
                    null,
                    errorMessages // Trả về mảng lỗi
            );
        }

        // Nếu không có lỗi, thực hiện cập nhật
        return cityService.update(id, city);
    }


    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCity(@PathVariable Long id) {
        return cityService.deleteById(id);
    }
}