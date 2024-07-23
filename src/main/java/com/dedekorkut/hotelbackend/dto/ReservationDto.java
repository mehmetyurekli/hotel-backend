package com.dedekorkut.hotelbackend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {

    private long id;
    private UserDto user;
    private RoomDto room;

    private LocalDate date;

    private LocalDate createdAt;

}
