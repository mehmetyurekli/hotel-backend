package com.dedekorkut.hotelbackend.dto;

import lombok.Builder;

@Builder
public class RoomDto {

    private long id;
    private HotelDto hotel;
    private String name;
    private int beds;
    private int capacity;

    public RoomDto() {}

    public RoomDto(Long id, HotelDto hotel, String name, int beds, int capacity) {
        this.id = id;
        this.hotel = hotel;
        this.name = name;
        this.beds = beds;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelDto hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setId(long id) {
        this.id = id;
    }

}
