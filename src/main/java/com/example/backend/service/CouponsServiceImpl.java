package com.example.backend.service;
import com.example.backend.entity.Coupons;
import com.example.backend.repository.CouponsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponsServiceImpl {
    private final CouponsRepository couponsRepository;

    public CouponsServiceImpl(CouponsRepository couponsRepository) {
        this.couponsRepository = couponsRepository;
    }

    public List<Coupons> findAll() {
        return couponsRepository.findAll();
    }

    public Coupons findById(Long id) {
        return couponsRepository.findById(id).orElse(null);
    }

    public Coupons save(Coupons coupons) {
        return couponsRepository.save(coupons);
    }

    public void deleteById(Long id) {
        couponsRepository.deleteById(id);
    }
}
