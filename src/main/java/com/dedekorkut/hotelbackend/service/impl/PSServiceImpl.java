package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.NewPackageServiceDto;
import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.dto.PackageServiceDto;
import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.entity.Package;
import com.dedekorkut.hotelbackend.entity.PackageService;
import com.dedekorkut.hotelbackend.mapper.PackageMapper;
import com.dedekorkut.hotelbackend.mapper.PackageServiceMapper;
import com.dedekorkut.hotelbackend.mapper.ServiceMapper;
import com.dedekorkut.hotelbackend.repository.PackageServiceRepository;
import com.dedekorkut.hotelbackend.service.PackageServiceService;
import com.dedekorkut.hotelbackend.service.ServiceService;
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
    public Optional<PackageServiceDto> findById(Long id) {
        return packageServiceRepository.findById(id).map(PackageServiceMapper::map);
    }

    @Override
    public List<ServiceDto> findServicesIncludedInPackage(Long packageId) {
        if(packageService.getPackageById(packageId).isEmpty()){
            throw new WillfulException("Package not found");
        }
        List<Long> serviceIds = packageServiceRepository.findServicesIncludedInPackage(packageId);
        List<ServiceDto> serviceDtos = new ArrayList<>();
        for(Long serviceId : serviceIds){
            serviceDtos.add(serviceService.getServiceById(serviceId).get());
        }
        return serviceDtos;
    }

    @Override
    public List<PackageServiceDto> save(NewPackageServiceDto newPackageServiceDto) {
        Optional<PackageDto> packageDto = packageService.getPackageById(newPackageServiceDto.getPackageId());

        List<ServiceDto> services = new ArrayList<>();

        for(Long serviceId : newPackageServiceDto.getServiceId()){
            Optional<ServiceDto> serviceDto = serviceService.getServiceById(serviceId);
            if(serviceDto.isEmpty()){
                throw new WillfulException("Service not found");
            }
            services.add(serviceDto.get());
        }

        if(packageDto.isEmpty()){
            throw new WillfulException("Package not found");
        }

        List<PackageService> saved = new ArrayList<>();
        for(ServiceDto serviceDto : services){
            PackageService packageService = PackageService.builder()
                    .aPackage(PackageMapper.map(packageDto.get()))
                    .service(ServiceMapper.map(serviceDto))
                    .build();
            saved.add(packageServiceRepository.save(packageService));
        }
        return saved.stream().map(PackageServiceMapper::map).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        packageServiceRepository.deleteById(id);
    }
}
