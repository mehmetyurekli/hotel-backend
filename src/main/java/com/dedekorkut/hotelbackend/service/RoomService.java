package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> findAll();
    List<RoomDto> findAllByHotelId(Long hotelId);
    List<RoomDto> findAvailable(Long hotelId, LocalDate start, LocalDate end);
    Optional<RoomDto> findById(long id);
    RoomDto save(RoomDto room, long hotelId);
    void deleteById(long id);
}
