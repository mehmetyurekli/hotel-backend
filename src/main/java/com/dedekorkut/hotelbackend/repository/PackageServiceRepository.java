package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.PackageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageServiceRepository extends JpaRepository<PackageService, Long> {

    @Query(nativeQuery = true, value = "SELECT ps.service_id FROM package_service ps JOIN packages p ON ps.package_id = p.id WHERE p.id = :packageId")
    List<Long> findServicesIncludedInPackage(@Param("packageId") Long packageId);
}
