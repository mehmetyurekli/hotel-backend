package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.entity.Rating;

public class RatingMapper {

    public static RatingDto map(Rating entity) {
        return RatingDto.builder()
                .id(entity.getId())
                .hotel(HotelMapper.map(entity.getHotel()))
                .user(UserMapper.map(entity.getUser()))
                .rating(entity.getRating())
                .build();
    }

    public static Rating map(RatingDto entity) {
        return Rating.builder()
                .id(entity.getId())
                .hotel(HotelMapper.map(entity.getHotel()))
                .user(UserMapper.map(entity.getUser()))
                .rating(entity.getRating())
                .build();
    }
}
