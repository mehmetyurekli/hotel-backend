package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.PackageDto;

import java.util.List;
import java.util.Optional;

public interface PackageService {

    List<PackageDto> getAllPackages();

    Optional<PackageDto> getPackageById(long id);

    PackageDto createPackage(String name);

    void deletePackage(long id);
}
