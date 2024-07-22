package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.mapper.ServiceMapper;
import com.dedekorkut.hotelbackend.repository.ServiceRepository;
import com.dedekorkut.hotelbackend.service.ServiceService;
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
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceDto> getServiceById(long id) {
        return serviceRepository.findById(id).map(ServiceMapper::map);
    }

    @Override
    public ServiceDto createService(String name) {
        com.dedekorkut.hotelbackend.entity.Service service = com.dedekorkut.hotelbackend.entity.Service.builder().name(name).build();
        service = serviceRepository.save(service);
        return ServiceMapper.map(service);
    }

    @Override
    public void deleteServiceById(long id) {
        serviceRepository.deleteById(id);
    }
}
