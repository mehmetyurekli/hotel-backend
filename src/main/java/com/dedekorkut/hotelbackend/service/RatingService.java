package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RatingDto;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<RatingDto> findAll();
    Optional<RatingDto> getRatingById(long id);
    RatingDto addRating(Long hotelId, Long userId, Double rating);
    void deleteRatingById(long id);

}
