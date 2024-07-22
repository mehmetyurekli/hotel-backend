package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ServiceDto;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    List<ServiceDto> getAllServices();
    Optional<ServiceDto> getServiceById(long id);
    ServiceDto createService(String name);
    void deleteServiceById(long id);

}
