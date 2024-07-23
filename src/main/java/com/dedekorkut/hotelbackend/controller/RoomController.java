package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.data.domain.Page;
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
    public Page<RoomDto> getAvailableRooms(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "limit", defaultValue = "10") int limit,
                                           @RequestBody RoomFilter filter) {

        return roomService.findAll(page, limit, filter);
    }

    @GetMapping
    public Page<RoomDto> getAllRooms(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "limit", defaultValue = "10") int limit){
        return roomService.findAll(page, limit);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Page<RoomDto>> findAllByHotelId(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                          @PathVariable Long hotelId) {
        Page<RoomDto> rooms = roomService.findAllByHotelId(page, limit, hotelId);
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
