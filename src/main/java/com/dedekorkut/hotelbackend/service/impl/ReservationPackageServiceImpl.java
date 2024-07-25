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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReservationPackageDto> getReservationPackageById(Long id) {
        if (id == null){
            throw new WillfulException("ReservationPackage id is null");
        }
        Optional<ReservationPackage> reservationPackage = reservationPackageRepository.findById(id);

        if (reservationPackage.isEmpty()){
            throw new WillfulException("ReservationPackage not found");
        }

        ReservationPackageDto reservationPackageDto = ReservationPackageMapper.map(reservationPackage.get());

        return ResponseEntity.ok(reservationPackageDto);
    }

    @Override
    public ResponseEntity<List<ReservationPackageDto>> createReservationPackage(NewReservationPackageDto newReservationPackageDto) {

        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Long reservationId : newReservationPackageDto.getReservationIds()) {
            ReservationDto reservationDto = reservationService.findByReservationId(reservationId).getBody();
            reservationDtos.add(reservationDto);
        }

        PackageDto packageDto = packageService.getPackageById(newReservationPackageDto.getPackageId()).getBody();

        List<ReservationPackage> reservationPackages = new ArrayList<>();
        for (ReservationDto reservationDto : reservationDtos) {
            assert packageDto != null;
            ReservationPackage reservationPackage = ReservationPackage.builder()
                    .reservation(ReservationMapper.convertToEntity(reservationDto))
                    .aPackage(PackageMapper.convertToEntity(packageDto))
                    .build();
            reservationPackages.add(reservationPackage);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                reservationPackages.stream().map(ReservationPackageMapper::map).collect(Collectors.toList())
        );
    }

    @Override
    public HttpStatus deleteReservationPackageById(Long id) {
        getReservationPackageById(id);

        reservationPackageRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
