package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_city")
    @SequenceGenerator(name = "id_city", sequenceName = "CITY_ID_SEQ", initialValue = 1, allocationSize = 1)
    private Long id_city;

    @Column(name = "name_city")
    @NotBlank(message = "Tên thành phố không được để trống và không được chứa khoảng trắng")
    @Size(min = 3, message = "Tên thành phố phải có ít nhất 3 ký tự")
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
