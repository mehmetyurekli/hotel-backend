package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.entity.Room;

public class RoomMapper {

    public static RoomDto map(Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .hotel(HotelMapper.map(entity.getHotel()))
                .name(entity.getName())
                .beds(entity.getBeds())
                .capacity(entity.getCapacity())
                .build();

    }

    public static RoomDto mapWithoutHotel(Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .beds(entity.getBeds())
                .capacity(entity.getCapacity())
                .build();

    }

    public static Room map(RoomDto dto) {
        return Room.builder()
                .id(dto.getId())
                .hotel(HotelMapper.map(dto.getHotel()))
                .name(dto.getName())
                .beds(dto.getBeds())
                .capacity(dto.getCapacity())
                .build();

    }
}
