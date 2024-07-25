package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.ServiceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    List<ServiceDto> getAllServices();

    ResponseEntity<ServiceDto> getServiceById(Long id);

    ResponseEntity<ServiceDto> createService(String name);

    HttpStatus deleteServiceById(Long id);

}
