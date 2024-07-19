package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.Room;
import com.dedekorkut.hotelbackend.mapper.ReservationMapper;
import com.dedekorkut.hotelbackend.mapper.RoomMapper;
import com.dedekorkut.hotelbackend.mapper.UserMapper;
import com.dedekorkut.hotelbackend.repository.ReservationRepository;
import com.dedekorkut.hotelbackend.service.ReservationService;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final RoomService roomService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserService userService, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public List<ReservationDto> findAllByUserId(long userId) {
        return reservationRepository.findAllByUserId(userId)
                .stream()
                .map(ReservationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getStays(long hotelId, long userId) {
        return reservationRepository.findAllByUser_IdAndRoom_Hotel_Id(userId, hotelId)
                .stream()
                .map(ReservationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDto> findByReservationId(long reservationId) {
        return reservationRepository.findById(reservationId).map(ReservationMapper::map);
    }

    @Override
    public List<RoomDto> findAvailableRooms(LocalDate start, LocalDate end) {

        return List.of();
    }

    @Override
    public List<ReservationDto> save(LocalDate start, LocalDate end, Long userId, Long roomId) {

        Optional<UserDto> userDto = userService.findById(userId);
        Optional<RoomDto> roomDto = roomService.findById(roomId);

        if(userDto.isEmpty() || roomDto.isEmpty()) {
            return null; //invalid room or user
        }

        if(!start.isBefore(end)){
            return null; //invalid dates
        }

        List<Reservation> reservations = new ArrayList<>();
        for (LocalDate date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)) {
            if(!isAvailable(date, roomId)){
                return null;
            }
            Reservation reservation = Reservation.builder()
                    .user(UserMapper.map(userDto.get()))
                    .room(RoomMapper.map(roomDto.get()))
                    .date(date)
                    .createdAt(LocalDate.now())
                    .build();
            reservations.add(reservation);
        }

        reservationRepository.saveAll(reservations);
        return reservations.stream()
                .map(ReservationMapper::map)
                .collect(Collectors.toList());

    }

    @Override
    public void cancelReservation(long reservationId) {
        Optional<ReservationDto> reservation = findByReservationId(reservationId);
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public void cancelReservation(long... reservationIds) {
        for (long reservationId : reservationIds) {
            cancelReservation(reservationId);
        }
    }

    @Override
    public boolean isAvailable(LocalDate date, Long roomId) {
        return reservationRepository.findByDateAndRoomId(date, roomId) == null;
    }
}
