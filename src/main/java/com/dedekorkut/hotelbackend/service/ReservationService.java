package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Page<ReservationDto> findAllByUserId(int page, int size, Long userId);

    List<ReservationDto> getStays(Long hotelId, Long userId);

    ResponseEntity<ReservationDto> findByReservationId(Long reservationId);

    ResponseEntity<List<ReservationDto>> save(NewReservationDto newReservationDto);

    HttpStatus cancelReservation(Long reservationId);

    HttpStatus cancelReservation(Long... reservationIds);

}
