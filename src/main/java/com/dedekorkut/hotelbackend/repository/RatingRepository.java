package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findRatingByHotelIdAndUserId(Long hotelId, Long userId);
    List<Rating> findAllByUserId(Long userId);
    List<Rating> findAllByHotelId(Long hotelId);

    @Query("SELECT avg(r.rating) from Rating r where r.hotel.id = :hotelId")
    Double avgRatingByHotelId(@Param("hotelId") Long hotelId);
}
