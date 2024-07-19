package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<ReservationDto> findAllByUserId(long userId);
    List<ReservationDto> getStays(long hotelId, long userId);
    Optional<ReservationDto> findByReservationId(long reservationId);
    List<RoomDto> findAvailableRooms(LocalDate start, LocalDate end);
    List<ReservationDto> save(LocalDate start, LocalDate end, Long userId, Long roomId);
    void cancelReservation(long reservationId);
    void cancelReservation(long... reservationIds);
    boolean isAvailable(LocalDate date, Long roomId);
}
