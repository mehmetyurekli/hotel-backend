package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.HotelDto;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<HotelDto> findAll();
    List<HotelDto> findAllByCity(String city);
    Optional<HotelDto> findById(long id);
    HotelDto save(HotelDto dto);
    void deleteById(long id);
}
