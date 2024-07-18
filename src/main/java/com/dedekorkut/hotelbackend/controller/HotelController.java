package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<HotelDto> getAllHotels() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        if(hotelService.findById(id).isPresent()) {
            return new ResponseEntity<>(hotelService.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        if (hotelDto.getName() == null || hotelDto.getAddress() == null ||
                hotelDto.getCity() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
