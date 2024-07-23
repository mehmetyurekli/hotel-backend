package com.dedekorkut.hotelbackend.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomFilter {

    private String hotelName;
    private String city;
    private String startDate;
    private String endDate;
    private Integer beds;
    private Integer capacity;
    private Double minimumRating;

}
