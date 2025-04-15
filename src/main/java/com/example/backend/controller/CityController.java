package com.example.backend.controller;

import com.example.backend.entity.City;
import com.example.backend.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityService.findById(id);
    }
    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityService.save(city);
    }
    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City city) {
        // Tìm city bằng id từ cơ sở dữ liệu
        City existingCity = cityService.findById(id);

        // Nếu thành phố không tồn tại, có thể ném ngoại lệ hoặc trả về lỗi
        if (existingCity == null) {
            throw new EntityNotFoundException("City with id " + id + " not found");
        }

        // Cập nhật các thông tin của city
        existingCity.setNameCity(city.getNameCity()); // Giả sử bạn muốn cập nhật tên thành phố
        // Cập nhật các thuộc tính khác của city ở đây nếu cần

        // Lưu lại thành phố đã được cập nhật
        return cityService.save(existingCity);
    }


    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteById(id);
    }
}
