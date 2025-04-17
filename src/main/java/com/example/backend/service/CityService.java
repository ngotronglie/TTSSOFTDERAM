package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.City;

import java.util.List;

public interface CityService {
    ApiResponse<List<City>> findAll();

    ApiResponse<City> findById(Long id);

    ApiResponse<City> save(City city);

    ApiResponse<City> update(Long id, City city);

    ApiResponse<String> deleteById(Long id);
}
