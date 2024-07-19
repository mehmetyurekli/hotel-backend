package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path = "/user/{userId}")
    public List<ReservationDto> getReservations(@PathVariable Long userId) {
        return reservationService.findAllByUserId(userId);
    }

    @PostMapping(path = "/user/{userId}/room/{roomId}/start/{start}/end/{end}")
    public ResponseEntity<List<ReservationDto>> createReservation(@PathVariable Long userId, @PathVariable Long roomId,
                                                                  @PathVariable @DateTimeFormat LocalDate start,
                                                                  @PathVariable @DateTimeFormat LocalDate end){
        List<ReservationDto> list = reservationService.save(start,end,userId,roomId);

        if(list == null || list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/cancel/room/{roomId}")
    public HttpStatus cancelReservation(@PathVariable Long roomId, @RequestParam long... reservationIds){
        reservationService.cancelReservation(reservationIds);
        return HttpStatus.NO_CONTENT;
    }
}
