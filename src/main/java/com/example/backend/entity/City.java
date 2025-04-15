package com.example.backend.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_city;

    @Column(name = "name_city", nullable = false)
    private String nameCity;

    // Getters and Setters
    public Long getId() {
        return id_city;
    }

    public void setId(Long id) {
        this.id_city = id;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

}
