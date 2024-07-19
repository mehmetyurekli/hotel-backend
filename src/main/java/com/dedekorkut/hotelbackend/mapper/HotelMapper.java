package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.entity.Hotel;

public class HotelMapper {

    public static HotelDto map(Hotel entity) {
        return HotelDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .city(entity.getCity())
                .address(entity.getAddress())
                .rating(entity.getRating())
                .build();

    }

    public static Hotel map(HotelDto dto) {
        return Hotel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .city(dto.getCity())
                .address(dto.getAddress())
                .rating(dto.getRating())
                .build();

    }
}
