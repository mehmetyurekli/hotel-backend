package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.PackageServiceDto;
import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.dto.input.NewPackageServiceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PackageServiceService {

    List<PackageServiceDto> findAll();

    ResponseEntity<PackageServiceDto> findById(Long id);

    List<ServiceDto> findServicesIncludedInPackage(Long packageId);

    ResponseEntity<List<PackageServiceDto>> save(NewPackageServiceDto newPackageServiceDto);

    HttpStatus deleteById(Long id);
}
