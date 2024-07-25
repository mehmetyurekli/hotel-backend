package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.dto.input.NewRatingDto;
import com.dedekorkut.hotelbackend.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public Page<RatingDto> getRatings(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        return ratingService.findAll(page, size);
    }

    @GetMapping("/hotel/{hotelId}")
    public Page<RatingDto> getRatingsByHotel(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                             @PathVariable Long hotelId) {
        return ratingService.findAllByHotelId(page, size, hotelId);
    }

    @GetMapping("/user/{userId}")
    public Page<RatingDto> getRatingsByUserId(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                              @PathVariable Long userId) {
        return ratingService.findAllByUserId(page, size, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<RatingDto> createRating(@RequestBody NewRatingDto newRatingDto) {
        return ratingService.addRating(newRatingDto);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteRatingById(@PathVariable long id) {
        return ratingService.deleteRatingById(id);
    }
}
