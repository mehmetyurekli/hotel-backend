package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public Page<HotelDto> getAllHotels(@RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return hotelService.findAll(page, limit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        if (hotelService.findById(id).isPresent()) {
            return new ResponseEntity<>(hotelService.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new")
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        return new ResponseEntity<>(hotelService.save(hotelDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteHotel(@PathVariable Long id) {
        if (hotelService.findById(id).isPresent()) {
            hotelService.deleteById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }
}
