package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Package;
import com.dedekorkut.hotelbackend.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
}
