package com.dedekorkut.hotelbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RoomDto {

    private long id;
    private HotelDto hotel;
    private String name;
    private int beds;
    private int capacity;
    private BigDecimal price;

    public RoomDto() {}

    public RoomDto(Long id, HotelDto hotel, String name, int beds, int capacity, BigDecimal price) {
        this.id = id;
        this.hotel = hotel;
        this.name = name;
        this.beds = beds;
        this.capacity = capacity;
        this.price = price;
    }

}
