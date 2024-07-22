package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ReservationPackageDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationPackageDto;

import java.util.List;
import java.util.Optional;

public interface ReservationPackageService {

    List<ReservationPackageDto> getAllReservationPackages();
    Optional<ReservationPackageDto> getReservationPackageById(long id);
    List<ReservationPackageDto> createReservationPackage(NewReservationPackageDto newReservationPackageDto);
    void deleteReservationPackageById(long id);
}
