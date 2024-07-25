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

    public static Rating convertToEntity(RatingDto dto) {
        return Rating.builder()
                .id(dto.getId())
                .hotel(HotelMapper.convertToEntity(dto.getHotel()))
                .user(UserMapper.convertToEntity(dto.getUser()))
                .rating(dto.getRating())
                .build();
    }
}
