package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.service.ReservationService;
import com.dedekorkut.hotelbackend.service.RoomService;
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

    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping(path = "/start/{start}/end/{end}")
    public List<RoomDto> getAvailableRooms(@PathVariable @DateTimeFormat LocalDate start,
                                           @PathVariable @DateTimeFormat LocalDate end) {

        return reservationService.findAvailableRooms(start, end);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomDto>> findAllByHotelId(@PathVariable Long hotelId) {
        List<RoomDto> rooms = roomService.findAllByHotelId(hotelId);
        if(rooms == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/hotel/{hotelId}")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto, @PathVariable Long hotelId) {

        RoomDto saved = roomService.save(roomDto, hotelId);

        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
