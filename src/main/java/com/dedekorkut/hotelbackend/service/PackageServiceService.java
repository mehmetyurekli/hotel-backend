package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.input.NewPackageServiceDto;
import com.dedekorkut.hotelbackend.dto.PackageServiceDto;
import com.dedekorkut.hotelbackend.dto.ServiceDto;

import java.util.List;
import java.util.Optional;

public interface PackageServiceService {

    List<PackageServiceDto> findAll();
    Optional<PackageServiceDto> findById(Long id);
    List<ServiceDto> findServicesIncludedInPackage(Long packageId);
    List<PackageServiceDto> save(NewPackageServiceDto newPackageServiceDto);
    void deleteById(Long id);
}
