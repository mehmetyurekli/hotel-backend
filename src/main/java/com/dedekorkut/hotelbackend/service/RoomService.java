package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    Page<RoomDto> findAll(int page, int size, RoomFilter filter);

    Page<RoomDto> findAll(int page, int size);

    Page<RoomDto> findAllByHotelId(int page, int size, Long hotelId);

    ResponseEntity<RoomDto> findById(Long id);

    ResponseEntity<RoomDto> save(NewRoomDto newRoomDto, Long hotelId);

    HttpStatus deleteById(Long id);
}
