package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<RoomDto>> findAllByHotelId(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                                          @PathVariable Long hotelId) {
        Page<RoomDto> rooms = roomService.findAllByHotelId(page, size, hotelId);
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
