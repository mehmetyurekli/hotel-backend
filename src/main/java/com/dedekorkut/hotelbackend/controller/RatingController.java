package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.service.RatingService;
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
    public List<RatingDto> getRatings() {
        return ratingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable long id) {
        if(ratingService.getRatingById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ratingService.getRatingById(id).get(), HttpStatus.OK);
    }

    @PostMapping(path = "/hotel/{hotelId}/user/{userId}/rating/{rating}")
    public ResponseEntity<RatingDto> createRating(@PathVariable Long hotelId,
                                                  @PathVariable Long userId,
                                                  @PathVariable Double rating) {

        RatingDto dto = ratingService.addRating(hotelId, userId, rating);
        if(dto == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteRatingById(@PathVariable long id) {
        if(ratingService.getRatingById(id).isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        ratingService.deleteRatingById(id);
        return HttpStatus.NO_CONTENT;
    }
}
