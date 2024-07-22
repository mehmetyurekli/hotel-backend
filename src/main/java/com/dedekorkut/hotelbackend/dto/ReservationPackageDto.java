package com.dedekorkut.hotelbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationPackageDto {
    private long id;
    private ReservationDto reservation;
    private PackageDto aPackage;
}
