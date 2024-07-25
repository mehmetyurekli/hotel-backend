package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.mapper.ServiceMapper;
import com.dedekorkut.hotelbackend.repository.ServiceRepository;
import com.dedekorkut.hotelbackend.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(ServiceMapper::map)
                .toList();
    }

    @Override
    public ResponseEntity<ServiceDto> getServiceById(Long id) {

        if(id == null){
            throw new WillfulException("Service id is null");
        }
        Optional<com.dedekorkut.hotelbackend.entity.Service> optionalService = serviceRepository.findById(id);

        if(optionalService.isEmpty()){
            throw new WillfulException("Service not found (id: " + id + ")");
        }

        ServiceDto serviceDto = ServiceMapper.map(optionalService.get());

        return ResponseEntity.ok(serviceDto);
    }

    @Override
    public ResponseEntity<ServiceDto> createService(String name) {
        if(name == null){
            throw new WillfulException("Service name is null");
        }
        com.dedekorkut.hotelbackend.entity.Service saved =
                com.dedekorkut.hotelbackend.entity.Service.builder().name(name).build();

        saved = serviceRepository.save(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ServiceMapper.map(saved));
    }

    @Override
    public HttpStatus deleteServiceById(Long id) {
        getServiceById(id);

        serviceRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
