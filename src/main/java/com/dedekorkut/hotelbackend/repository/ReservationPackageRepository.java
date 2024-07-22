package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.ReservationPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationPackageRepository extends JpaRepository<ReservationPackage, Long> {
}
