package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long>, PagingAndSortingRepository<Package, Long> {
}
