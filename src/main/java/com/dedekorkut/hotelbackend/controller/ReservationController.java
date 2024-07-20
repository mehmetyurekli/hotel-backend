package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.NewReservationDto;
import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
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

    @GetMapping(path = "/start/{start}/end/{end}")
    public List<RoomDto> findAvailableRooms(@PathVariable @DateTimeFormat LocalDate start,
                                            @PathVariable @DateTimeFormat LocalDate end) {
        return reservationService.findAvailableRooms(start, end);
    }

    @PostMapping()
    public ResponseEntity<List<ReservationDto>> createReservation(@RequestBody NewReservationDto newReservationDto){
        List<ReservationDto> list = reservationService.save(newReservationDto);

        if(list == null || list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/cancel")
    public HttpStatus cancelReservation(@RequestParam long... reservationIds){
        reservationService.cancelReservation(reservationIds);
        return HttpStatus.NO_CONTENT;
    }

}
