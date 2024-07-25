package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationDto;
import com.dedekorkut.hotelbackend.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path = "/user/{userId}")
    public Page<ReservationDto> getReservations(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size,
                                                @PathVariable Long userId) {
        return reservationService.findAllByUserId(page, size, userId);
    }

    @PostMapping("/new")
    public ResponseEntity<List<ReservationDto>> createReservation(@RequestBody NewReservationDto newReservationDto) {
        List<ReservationDto> list = reservationService.save(newReservationDto);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/cancel")
    public HttpStatus cancelReservation(@RequestParam long... reservationIds) {
        reservationService.cancelReservation(reservationIds);
        return HttpStatus.NO_CONTENT;
    }

}
