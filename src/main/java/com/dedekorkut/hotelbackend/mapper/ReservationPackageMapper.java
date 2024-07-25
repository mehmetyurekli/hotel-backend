package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.ReservationPackageDto;
import com.dedekorkut.hotelbackend.entity.ReservationPackage;

public class ReservationPackageMapper {

    public static ReservationPackageDto map(ReservationPackage reservationPackage) {

        return ReservationPackageDto.builder()
                .id(reservationPackage.getId())
                .reservation(ReservationMapper.map(reservationPackage.getReservation()))
                .aPackage(PackageMapper.map(reservationPackage.getAPackage()))
                .build();
    }

    public static ReservationPackage convertToEntity(ReservationPackageDto reservationPackage) {

        return ReservationPackage.builder()
                .id(reservationPackage.getId())
                .reservation(ReservationMapper.convertToEntity(reservationPackage.getReservation()))
                .aPackage(PackageMapper.convertToEntity(reservationPackage.getAPackage()))
                .build();
    }
}
