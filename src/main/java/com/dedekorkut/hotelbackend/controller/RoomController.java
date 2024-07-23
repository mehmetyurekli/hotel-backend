package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
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

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> findById(@PathVariable Long id) {
        Optional<RoomDto> roomDto = roomService.findById(id);
        if (roomDto.isPresent()) {
            return new ResponseEntity<>(roomDto.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new/hotel/{hotelId}")
    public ResponseEntity<RoomDto> createRoom(@RequestBody NewRoomDto newRoomDto, @PathVariable Long hotelId) {

        RoomDto saved = roomService.save(newRoomDto, hotelId);

        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteRoom(@PathVariable Long id) {
        if (roomService.findById(id).isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        roomService.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
