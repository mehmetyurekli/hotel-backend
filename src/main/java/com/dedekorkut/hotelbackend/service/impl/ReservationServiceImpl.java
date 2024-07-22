package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.NewReservationDto;
import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.mapper.ReservationMapper;
import com.dedekorkut.hotelbackend.mapper.RoomMapper;
import com.dedekorkut.hotelbackend.mapper.UserMapper;
import com.dedekorkut.hotelbackend.repository.ReservationRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import com.dedekorkut.hotelbackend.service.ReservationService;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final HotelService hotelService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserService userService, RoomService roomService, HotelService hotelService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @Override
    public List<ReservationDto> findAllByUserId(long userId) {
        if(userService.findById(userId).isEmpty()){
            throw new WillfulException("User not found");
        }
        return reservationRepository.findAllByUserId(userId)
                .stream()
                .map(ReservationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getStays(long hotelId, long userId) {
        if(userService.findById(userId).isEmpty()){
            throw new WillfulException("User not found");
        }
        if(hotelService.findById(hotelId).isEmpty()){
            throw new WillfulException("Hotel not found");
        }
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
        List<Long> ids = reservationRepository.findAvailableRooms(Date.valueOf(start), Date.valueOf(end));
        List<RoomDto> rooms = new ArrayList<>();
        for(Long id : ids){
            rooms.add(roomService.findById(id).get());
        }
        return rooms;
    }

    @Override
    public List<ReservationDto> save(NewReservationDto newReservationDto) {

        if(newReservationDto.getRoomId() == null || newReservationDto.getUserId() == null ||
        newReservationDto.getStart() == null || newReservationDto.getEnd() == null) {
            throw new WillfulException("Missing a field from (roomId, userId, start, end)");
        }
        LocalDate start = newReservationDto.getStart();
        LocalDate end = newReservationDto.getEnd();

        Optional<UserDto> userDto = userService.findById(newReservationDto.getUserId());
        Optional<RoomDto> roomDto = roomService.findById(newReservationDto.getRoomId());

        if(userDto.isEmpty() || roomDto.isEmpty()) {
            throw new WillfulException("Room or user not found");
        }

        if(!start.isBefore(end) && !start.isEqual(end)){
            throw new WillfulException("Invalid reservation dates");
        }

        List<Reservation> reservations = new ArrayList<>();
        for (LocalDate date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)) {
            if(!isAvailable(date, newReservationDto.getRoomId())){
                throw new WillfulException("Room not available on given date interval");
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
