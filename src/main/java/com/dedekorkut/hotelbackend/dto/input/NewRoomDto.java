package com.dedekorkut.hotelbackend.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewRoomDto {

    private Long internalId;
    private String name;
    private Integer beds;
    private Integer capacity;
    private BigDecimal price;
}
