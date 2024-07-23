package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.dto.input.NewRatingDto;
import com.dedekorkut.hotelbackend.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public Page<RatingDto> getRatings(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ratingService.findAll(page, limit);
    }

    @GetMapping("/hotel/{hotelId}")
    public Page<RatingDto> getRatingsByHotel(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "limit", defaultValue = "10") int limit,
                                             @PathVariable long hotelId) {
        return ratingService.findAllByHotelId(page, limit, hotelId);
    }

    @GetMapping("/user/{userId}")
    public Page<RatingDto> getRatingsByUserId(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "limit", defaultValue = "10") int limit,
                                              @PathVariable long userId) {
        return ratingService.findAllByUserId(page, limit, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable long id) {
        if (ratingService.getRatingById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ratingService.getRatingById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<RatingDto> createRating(@RequestBody NewRatingDto newRatingDto) {
        RatingDto dto = ratingService.addRating(newRatingDto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteRatingById(@PathVariable long id) {
        if (ratingService.getRatingById(id).isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        ratingService.deleteRatingById(id);
        return HttpStatus.NO_CONTENT;
    }
}
