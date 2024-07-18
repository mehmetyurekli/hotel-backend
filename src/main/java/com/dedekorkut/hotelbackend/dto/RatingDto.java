package com.dedekorkut.hotelbackend.dto;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.User;
import lombok.Builder;

@Builder
public class RatingDto {

    private long id;
    private HotelDto hotel;
    private UserDto user;
    private double rating;
    public RatingDto() {}

    public RatingDto(long id, HotelDto hotel, UserDto user, double rating) {
        this.id = id;
        this.hotel = hotel;
        this.user = user;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HotelDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelDto hotel) {
        this.hotel = hotel;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
