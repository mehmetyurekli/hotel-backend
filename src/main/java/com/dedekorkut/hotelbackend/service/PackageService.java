package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.PackageDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PackageService {

    Page<PackageDto> getAllPackages(int page, int size);

    ResponseEntity<PackageDto> getPackageById(Long id);

    ResponseEntity<PackageDto> createPackage(String name);

    HttpStatus deletePackage(Long id);
}
