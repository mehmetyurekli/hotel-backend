package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.entity.Package;
import com.dedekorkut.hotelbackend.mapper.PackageMapper;
import com.dedekorkut.hotelbackend.repository.PackageRepository;
import com.dedekorkut.hotelbackend.service.PackageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public List<PackageDto> getAllPackages() {
        return packageRepository.findAll()
                .stream().map(PackageMapper::map).collect(Collectors.toList());
    }

    @Override
    public Optional<PackageDto> getPackageById(long id) {
        return packageRepository.findById(id).map(PackageMapper::map);
    }

    @Override
    public PackageDto createPackage(String name) {
        Package aPackage = Package.builder().name(name).build();
        aPackage = packageRepository.save(aPackage);
        return PackageMapper.map(aPackage);
    }

    @Override
    public void deletePackage(long id) {
        packageRepository.deleteById(id);
    }
}
