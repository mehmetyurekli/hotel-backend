package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ReservationPackageDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationPackageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationPackageService {

    List<ReservationPackageDto> getAllReservationPackages();

    ResponseEntity<ReservationPackageDto> getReservationPackageById(Long id);

    ResponseEntity<List<ReservationPackageDto>> createReservationPackage(NewReservationPackageDto newReservationPackageDto);

    HttpStatus deleteReservationPackageById(Long id);
}
