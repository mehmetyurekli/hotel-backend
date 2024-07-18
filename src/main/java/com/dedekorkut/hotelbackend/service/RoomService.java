package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> findAll();
    Optional<RoomDto> findById(long id);
    RoomDto save(RoomDto room);
    void deleteById(long id);
}
