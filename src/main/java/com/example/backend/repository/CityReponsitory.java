package com.example.backend.repository;

import com.example.backend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityReponsitory extends JpaRepository<City, Long> {

}



