package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.dto.input.NewRatingDto;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<RatingDto> findAll();

    List<RatingDto> findAllByUserId(long userId);

    List<RatingDto> findAllByHotelId(long hotelId);

    Optional<RatingDto> getRatingById(long id);

    RatingDto addRating(NewRatingDto newRatingDto);

    void deleteRatingById(long id);

}
