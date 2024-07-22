package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.NewPackageServiceDto;
import com.dedekorkut.hotelbackend.dto.PackageServiceDto;
import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.service.PackageServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/package-service")
public class PackageServiceController {

    private final PackageServiceService packageServiceService;

    public PackageServiceController(PackageServiceService packageServiceService) {
        this.packageServiceService = packageServiceService;
    }

    @GetMapping
    public List<PackageServiceDto> getAllPackageServices() {
        return packageServiceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageServiceDto> getPackageServiceById(@PathVariable("id") Long id) {
        if(packageServiceService.findById(id).isPresent()) {
            return new ResponseEntity<>(packageServiceService.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/package/{packageId}")
    public List<ServiceDto> getPackageServicesByPackageId(@PathVariable("packageId") Long packageId) {
        return packageServiceService.findServicesIncludedInPackage(packageId);
    }

    @PostMapping
    public List<PackageServiceDto> createPackageService(@RequestBody NewPackageServiceDto newPackageServiceDto) {
        return packageServiceService.save(newPackageServiceDto);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePackageServiceById(@PathVariable("id") Long id) {
        if(packageServiceService.findById(id).isPresent()) {
            packageServiceService.deleteById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }
}
