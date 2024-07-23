package com.dedekorkut.hotelbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationPackageDto {
    private long id;
    private ReservationDto reservation;
    private PackageDto aPackage;
}
