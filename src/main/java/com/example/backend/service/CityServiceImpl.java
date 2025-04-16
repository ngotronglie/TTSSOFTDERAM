package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.City;
import com.example.backend.repository.CityReponsitory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityReponsitory cityRepository;

    public CityServiceImpl(CityReponsitory cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public ApiResponse save(City city) {
        try {
            cityRepository.save(city);
        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return new ApiResponse(
                "success",
                "Tạo thành phố thành công",
                LocalDateTime.now(),
                1,
                null
        );
    }

    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
