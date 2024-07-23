package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.dto.input.NewRatingDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RatingService {

    Page<RatingDto> findAll(int page, int limit);

    Page<RatingDto> findAllByUserId(int page, int limit, long userId);

    Page<RatingDto> findAllByHotelId(int page, int limit, long hotelId);

    Optional<RatingDto> getRatingById(long id);

    RatingDto addRating(NewRatingDto newRatingDto);

    void deleteRatingById(long id);

}
