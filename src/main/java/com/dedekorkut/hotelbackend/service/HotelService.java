package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.HotelDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface HotelService {
    Page<HotelDto> findAll(int page, int limit);

    Optional<HotelDto> findById(long id);

    HotelDto save(HotelDto dto);

    void deleteById(long id);
}
