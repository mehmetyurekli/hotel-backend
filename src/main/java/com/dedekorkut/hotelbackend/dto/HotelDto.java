package com.dedekorkut.hotelbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDto {

    private long id;
    private String name;
    private String city;
    private String address;
    private Double rating;

}
