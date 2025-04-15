package com.example.backend.service;

import com.example.backend.entity.City;
import com.example.backend.repository.CityReponsitory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityReponsitory cityRepository;

    public CityService(CityReponsitory cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
