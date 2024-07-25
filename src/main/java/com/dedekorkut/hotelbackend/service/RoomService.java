package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoomService {
    Page<RoomDto> findAll(int page, int size, RoomFilter filter);

    Page<RoomDto> findAll(int page, int size);

    Page<RoomDto> findAllByHotelId(int page, int size, Long hotelId);

    Optional<RoomDto> findById(long id);

    RoomDto save(NewRoomDto newRoomDto, long hotelId);

    void deleteById(long id);
}
