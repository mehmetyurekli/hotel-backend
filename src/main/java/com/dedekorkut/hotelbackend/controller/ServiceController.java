package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<ServiceDto> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getServiceById(@PathVariable("id") Long id) {
        if (serviceService.getServiceById(id).isPresent()) {
            return new ResponseEntity<>(serviceService.getServiceById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ServiceDto createService(@RequestParam String name) {
        return serviceService.createService(name);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteService(@PathVariable("id") Long id) {
        if (serviceService.getServiceById(id).isPresent()) {
            serviceService.deleteServiceById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }
}
