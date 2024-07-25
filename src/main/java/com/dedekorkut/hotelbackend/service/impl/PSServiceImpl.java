package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.dto.PackageServiceDto;
import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.dto.input.NewPackageServiceDto;
import com.dedekorkut.hotelbackend.entity.PackageService;
import com.dedekorkut.hotelbackend.mapper.PackageMapper;
import com.dedekorkut.hotelbackend.mapper.PackageServiceMapper;
import com.dedekorkut.hotelbackend.mapper.ServiceMapper;
import com.dedekorkut.hotelbackend.repository.PackageServiceRepository;
import com.dedekorkut.hotelbackend.service.PackageServiceService;
import com.dedekorkut.hotelbackend.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PSServiceImpl implements PackageServiceService {

    private final PackageServiceRepository packageServiceRepository;
    private final ServiceService serviceService;
    private final com.dedekorkut.hotelbackend.service.PackageService packageService;

    public PSServiceImpl(PackageServiceRepository packageServiceRepository, ServiceService serviceService, com.dedekorkut.hotelbackend.service.PackageService packageService) {
        this.packageServiceRepository = packageServiceRepository;
        this.serviceService = serviceService;
        this.packageService = packageService;
    }

    @Override
    public List<PackageServiceDto> findAll() {
        return packageServiceRepository.findAll()
                .stream()
                .map(PackageServiceMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<PackageServiceDto> findById(Long id) {
        if(id == null){
            throw new WillfulException("PackageService id is null");
        }

        Optional<PackageService> optional = packageServiceRepository.findById(id);

        if(optional.isEmpty()){
            throw new WillfulException("PackageService not found (id: " + id + ")");
        }

        PackageServiceDto packageServiceDto = PackageServiceMapper.map(optional.get());

        return ResponseEntity.ok(packageServiceDto);
    }

    @Override
    public List<ServiceDto> findServicesIncludedInPackage(Long packageId) {

        packageService.getPackageById(packageId);

        List<Long> serviceIds = packageServiceRepository.findServicesIncludedInPackage(packageId);
        List<ServiceDto> serviceDtos = new ArrayList<>();
        for (Long serviceId : serviceIds) {
            serviceDtos.add(serviceService.getServiceById(serviceId).getBody());
        }
        return serviceDtos;
    }

    @Override
    public ResponseEntity<List<PackageServiceDto>> save(NewPackageServiceDto newPackageServiceDto) {

        if (newPackageServiceDto.getServiceId() == null || newPackageServiceDto.getPackageId() == null) {
            throw new WillfulException("Missing a field from (service_id, package_id)");
        }

        PackageDto packageDto = packageService.getPackageById(newPackageServiceDto.getPackageId()).getBody();

        List<ServiceDto> services = new ArrayList<>();

        for (Long serviceId : newPackageServiceDto.getServiceId()) {
            ServiceDto serviceDto = serviceService.getServiceById(serviceId).getBody();
            services.add(serviceDto);
        }

        List<PackageService> saved = new ArrayList<>();
        for (ServiceDto serviceDto : services) {
            assert packageDto != null;
            PackageService packageService = PackageService.builder()
                    .aPackage(PackageMapper.convertToEntity(packageDto))
                    .service(ServiceMapper.convertToEntity(serviceDto))
                    .build();
            saved.add(packageServiceRepository.save(packageService));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved.stream().map(PackageServiceMapper::map).collect(Collectors.toList()));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        findById(id);

        packageServiceRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
