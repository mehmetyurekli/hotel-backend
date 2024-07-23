package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Page<ReservationDto> findAllByUserId(int page, int limit, long userId);

    List<ReservationDto> getStays(long hotelId, long userId);

    Optional<ReservationDto> findByReservationId(long reservationId);

    List<ReservationDto> save(NewReservationDto newReservationDto);

    void cancelReservation(long reservationId);

    void cancelReservation(long... reservationIds);

    boolean isAvailable(LocalDate date, Long roomId);
}
