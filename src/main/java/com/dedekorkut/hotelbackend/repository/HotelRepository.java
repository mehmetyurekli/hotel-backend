package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, PagingAndSortingRepository<Hotel, Long> {
    List<Hotel> findByCity(String city);
}
