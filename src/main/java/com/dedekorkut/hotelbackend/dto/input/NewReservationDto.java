package com.dedekorkut.hotelbackend.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewReservationDto {

    private Long userId;
    private Long roomId;
    private String start;
    private String end;

}
