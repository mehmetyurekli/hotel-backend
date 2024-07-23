package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.dto.ReservationDto;
import com.dedekorkut.hotelbackend.dto.ReservationPackageDto;
import com.dedekorkut.hotelbackend.dto.input.NewReservationPackageDto;
import com.dedekorkut.hotelbackend.entity.ReservationPackage;
import com.dedekorkut.hotelbackend.mapper.PackageMapper;
import com.dedekorkut.hotelbackend.mapper.ReservationMapper;
import com.dedekorkut.hotelbackend.mapper.ReservationPackageMapper;
import com.dedekorkut.hotelbackend.repository.ReservationPackageRepository;
import com.dedekorkut.hotelbackend.service.PackageService;
import com.dedekorkut.hotelbackend.service.ReservationPackageService;
import com.dedekorkut.hotelbackend.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationPackageServiceImpl implements ReservationPackageService {

    private final ReservationPackageRepository reservationPackageRepository;
    private final ReservationService reservationService;
    private final PackageService packageService;

    public ReservationPackageServiceImpl(ReservationPackageRepository reservationPackageRepository, ReservationService reservationService, PackageService packageService) {
        this.reservationPackageRepository = reservationPackageRepository;
        this.reservationService = reservationService;
        this.packageService = packageService;
    }

    @Override
    public List<ReservationPackageDto> getAllReservationPackages() {
        return reservationPackageRepository.findAll()
                .stream()
                .map(ReservationPackageMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationPackageDto> getReservationPackageById(long id) {
        return reservationPackageRepository.findById(id).map(ReservationPackageMapper::map);
    }

    @Override
    public List<ReservationPackageDto> createReservationPackage(NewReservationPackageDto newReservationPackageDto) {

        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Long reservationId : newReservationPackageDto.getReservationIds()) {
            Optional<ReservationDto> reservationDto = reservationService.findByReservationId(reservationId);
            if (reservationDto.isEmpty()) {
                throw new WillfulException("Reservation with id " + reservationId + " not found");
            }
            reservationDtos.add(reservationDto.get());
        }

        Optional<PackageDto> packageDto = packageService.getPackageById(newReservationPackageDto.getPackageId());

        if (packageDto.isEmpty()) {
            throw new WillfulException("Package not found");
        }

        List<ReservationPackage> reservationPackages = new ArrayList<>();
        for (ReservationDto reservationDto : reservationDtos) {
            ReservationPackage reservationPackage = ReservationPackage.builder()
                    .reservation(ReservationMapper.map(reservationDto))
                    .aPackage(PackageMapper.map(packageDto.get()))
                    .build();
        }

        return reservationPackages.stream().map(ReservationPackageMapper::map).collect(Collectors.toList());
    }

    @Override
    public void deleteReservationPackageById(long id) {
        reservationPackageRepository.deleteById(id);
    }
}
