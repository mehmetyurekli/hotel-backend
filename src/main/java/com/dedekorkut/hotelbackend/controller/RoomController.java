package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.service.ReservationService;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final ReservationService reservationService;

    public RoomController(RoomService roomService, ReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @GetMapping(path = "/filter")
    public List<RoomDto> getAvailableRooms(@RequestBody RoomFilter filter) {

        return roomService.findAll(filter);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomDto>> findAllByHotelId(@PathVariable Long hotelId) {
        List<RoomDto> rooms = roomService.findAllByHotelId(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/new/hotel/{hotelId}")
    public ResponseEntity<RoomDto> createRoom(@RequestBody NewRoomDto newRoomDto, @PathVariable Long hotelId) {

        RoomDto saved = roomService.save(newRoomDto, hotelId);

        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
