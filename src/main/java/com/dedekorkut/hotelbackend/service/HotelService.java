package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.HotelDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface HotelService {
    Page<HotelDto> findAll(int page, int size);

    ResponseEntity<HotelDto> findById(Long id);

    ResponseEntity<HotelDto> save(HotelDto dto);

    HttpStatus deleteById(Long id);
}
