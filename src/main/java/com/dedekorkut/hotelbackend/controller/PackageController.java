package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.service.PackageService;
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
    public List<PackageDto> getAllPackages() {
        return packageService.getAllPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageDto> getPackageById(@PathVariable("id") long id) {
        if (packageService.getPackageById(id).isPresent()) {
            return new ResponseEntity<>(packageService.getPackageById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public PackageDto createPackage(@RequestParam String name) {
        return packageService.createPackage(name);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePackageById(@PathVariable("id") long id) {
        if (packageService.getPackageById(id).isPresent()) {
            packageService.deletePackage(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

}
