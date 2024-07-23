package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.specification.RoomFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> findAll(RoomFilter filter);
    List<RoomDto> findAllByHotelId(Long hotelId);
    Optional<RoomDto> findById(long id);
    RoomDto save(NewRoomDto newRoomDto, long hotelId);
    void deleteById(long id);
}
