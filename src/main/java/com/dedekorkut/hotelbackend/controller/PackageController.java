package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.service.PackageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public Page<PackageDto> getAllPackages(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return packageService.getAllPackages(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageDto> getPackageById(@PathVariable("id") long id) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<PackageDto> createPackage(@RequestParam String name) {
        return packageService.createPackage(name);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePackageById(@PathVariable("id") long id) {
        return packageService.deletePackage(id);
    }

}
