package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(path = "/filter")
    public Page<RoomDto> getAvailableRooms(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           @RequestBody RoomFilter filter) {

        return roomService.findAll(page, size, filter);
    }

    @GetMapping
    public Page<RoomDto> getAllRooms(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        return roomService.findAll(page, size);
    }

    @GetMapping("/hotel/{hotelId}")
    public Page<RoomDto> findAllByHotelId(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                                          @PathVariable Long hotelId) {
        return roomService.findAllByHotelId(page, size, hotelId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> findById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @PostMapping("/new/hotel/{hotelId}")
    public ResponseEntity<RoomDto> createRoom(@RequestBody NewRoomDto newRoomDto, @PathVariable Long hotelId) {
        return roomService.save(newRoomDto, hotelId);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteRoom(@PathVariable Long id) {
        return roomService.deleteById(id);
    }
}
