package com.dedekorkut.hotelbackend.dto;

import lombok.Builder;

@Builder
public class HotelDto {

    private long id;
    private String name;
    private String city;
    private String address;
    private Double rating;

    public HotelDto(){}

    public HotelDto(Long id, String name, String city, String address, Double rating) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
