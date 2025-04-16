package com.example.backend.service;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.City;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CityService {
//
//    List<City> findAll();
//
//     City findById(Long id);

    ApiResponse save(City city);

//     void deleteById(Long id);
}
