package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationDto;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.mapper.ReservationMapper;
import com.dedekorkut.hotelbackend.mapper.RoomMapper;
import com.dedekorkut.hotelbackend.mapper.UserMapper;
import com.dedekorkut.hotelbackend.repository.ReservationRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import com.dedekorkut.hotelbackend.service.ReservationService;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Page<ReservationDto> findAllByUserId(int page, int size, Long userId) {

        userService.findById(userId).getBody();

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Reservation> pages = reservationRepository.findAllByUserId(userId, pageable);

        return pages.map(ReservationMapper::map);
    }

    @Override
    public List<ReservationDto> getStays(Long hotelId, Long userId) {
        userService.findById(userId);
        hotelService.findById(hotelId);

        return reservationRepository.findAllByUser_IdAndRoom_Hotel_Id(userId, hotelId)
                .stream()
                .map(ReservationMapper::map)
                .toList();
    }

    @Override
    public ResponseEntity<ReservationDto> findByReservationId(Long reservationId) {
        if(reservationId == null){
            throw new WillfulException("ReservationId is null");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if(optionalReservation.isEmpty()){
            throw new WillfulException("Reservation not found (id: " + reservationId + ")");
        }
        ReservationDto reservationDto = ReservationMapper.map(optionalReservation.get());
        return ResponseEntity.ok(reservationDto);
    }

    @Override
    public ResponseEntity<List<ReservationDto>> save(NewReservationDto newReservationDto) {

        if (newReservationDto.getRoomId() == null || newReservationDto.getUserId() == null ||
                newReservationDto.getStart() == null || newReservationDto.getEnd() == null) {
            throw new WillfulException("Missing a field from (roomId, userId, start, end)");
        }

        LocalDate start = LocalDate.parse(newReservationDto.getStart());
        LocalDate end = LocalDate.parse(newReservationDto.getEnd());

        UserDto userDto = userService.findById(newReservationDto.getUserId()).getBody();
        RoomDto roomDto = roomService.findById(newReservationDto.getRoomId()).getBody();

        List<Reservation> reservations = new ArrayList<>();
        for (LocalDate date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1)) {
            if (!isAvailable(date, newReservationDto.getRoomId())) {
                throw new WillfulException("Room not available on given date interval");
            }
            assert roomDto != null;
            assert userDto != null;
            Reservation reservation = Reservation.builder()
                    .user(UserMapper.map(userDto))
                    .room(RoomMapper.map(roomDto))
                    .date(date)
                    .createdAt(LocalDate.now())
                    .build();
            reservations.add(reservation);
        }

        reservationRepository.saveAll(reservations);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                reservations.stream()
                        .map(ReservationMapper::map)
                        .collect(Collectors.toList())
        );

    }

    @Override
    public HttpStatus cancelReservation(Long reservationId) {
        findByReservationId(reservationId);

        reservationRepository.deleteById(reservationId);
        return HttpStatus.NO_CONTENT;
    }

    @Override
    public HttpStatus cancelReservation(Long... reservationIds) {
        for (long reservationId : reservationIds) {
            findByReservationId(reservationId);
        }
        for (Long reservationId : reservationIds) {
            cancelReservation(reservationId);
        }
        return HttpStatus.NO_CONTENT;
    }

    private boolean isAvailable(LocalDate date, Long roomId) {
        return reservationRepository.findByDateAndRoomId(date, roomId) == null;
    }
}
