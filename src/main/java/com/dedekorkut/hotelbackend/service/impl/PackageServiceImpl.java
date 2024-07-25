package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.entity.Package;
import com.dedekorkut.hotelbackend.mapper.PackageMapper;
import com.dedekorkut.hotelbackend.repository.PackageRepository;
import com.dedekorkut.hotelbackend.service.PackageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public Page<PackageDto> getAllPackages(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Package> pages = packageRepository.findAll(pageable);

        return pages.map(PackageMapper::map);
    }

    @Override
    public ResponseEntity<PackageDto> getPackageById(Long id) {

        if(id == null){
            throw new WillfulException("Package id is null");
        }

        Optional<Package> optionalPackage = packageRepository.findById(id);

        if(optionalPackage.isEmpty()){
            throw new WillfulException("Package not found (id: " + id + ")");
        }

        PackageDto packageDto = PackageMapper.map(optionalPackage.get());

        return ResponseEntity.ok(packageDto);
    }

    @Override
    public ResponseEntity<PackageDto> createPackage(String name) {

        if(name == null){
            throw new WillfulException("Package name is null");
        }

        Package saved = Package.builder().name(name).build();
        saved = packageRepository.save(saved);

        return ResponseEntity.ok(PackageMapper.map(saved));
    }

    @Override
    public HttpStatus deletePackage(Long id) {
        getPackageById(id);
        packageRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
