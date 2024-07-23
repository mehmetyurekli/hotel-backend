package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findRatingByHotelIdAndUserId(Long hotelId, Long userId);

    Page<Rating> findAllByUserId(Long userId, Pageable pageable);

    Page<Rating> findAllByHotelId(Long hotelId, Pageable pageable);

    @Query("SELECT avg(r.rating) from Rating r where r.hotel.id = :hotelId")
    Double avgRatingByHotelId(@Param("hotelId") Long hotelId);
}
