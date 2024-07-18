package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path = "/{userId}")
    public List<Reservation> getReservations(@PathVariable Long userId) {
        return reservationService.findAllByUserId(userId);
    }
}
