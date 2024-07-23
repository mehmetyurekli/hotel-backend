package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.entity.Room;

public class RoomMapper {

    public static RoomDto map(Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .internalId(entity.getInternalId())
                .hotel(HotelMapper.map(entity.getHotel()))
                .name(entity.getName())
                .beds(entity.getBeds())
                .capacity(entity.getCapacity())
                .price(entity.getPrice())
                .build();

    }

    public static RoomDto mapWithoutHotel(Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .internalId(entity.getInternalId())
                .name(entity.getName())
                .beds(entity.getBeds())
                .capacity(entity.getCapacity())
                .price(entity.getPrice())
                .build();

    }

    public static Room map(RoomDto dto) {
        return Room.builder()
                .id(dto.getId())
                .internalId(dto.getInternalId())
                .hotel(HotelMapper.map(dto.getHotel()))
                .name(dto.getName())
                .beds(dto.getBeds())
                .capacity(dto.getCapacity())
                .price(dto.getPrice())
                .build();

    }
}
