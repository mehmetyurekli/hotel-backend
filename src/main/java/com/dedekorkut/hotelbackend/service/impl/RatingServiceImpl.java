package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.dto.RatingDto;
import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.entity.Rating;
import com.dedekorkut.hotelbackend.mapper.HotelMapper;
import com.dedekorkut.hotelbackend.mapper.RatingMapper;
import com.dedekorkut.hotelbackend.mapper.UserMapper;
import com.dedekorkut.hotelbackend.repository.RatingRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import com.dedekorkut.hotelbackend.service.RatingService;
import com.dedekorkut.hotelbackend.service.ReservationService;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final HotelService hotelService;
    private final UserService userService;
    private final ReservationService reservationService;

    public RatingServiceImpl(RatingRepository ratingRepository, HotelService hotelService, UserService userService, ReservationService reservationService) {
        this.ratingRepository = ratingRepository;
        this.hotelService = hotelService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @Override
    public List<RatingDto> findAll() {
        return ratingRepository.findAll()
                .stream().map(RatingMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RatingDto> getRatingById(long id) {
        return ratingRepository.findById(id).map(RatingMapper::map);
    }

    @Override
    public RatingDto addRating(Long hotelId, Long userId, Double rating) {
        Optional<UserDto> user = userService.findById(userId);
        Optional<HotelDto> hotel = hotelService.findById(hotelId);

        if(hotel.isEmpty() || user.isEmpty()) {
            return null;
        }
        if(reservationService.getStays(hotelId, userId).isEmpty()) {
            return null; //no reservation, cant review hotel
        }

        Optional<Rating> existing = ratingRepository.findRatingByHotelIdAndUserId(hotelId, userId);
        if(existing.isPresent()) {
            Rating replaced = existing.get();
            replaced.setRating(rating);
            replaced = ratingRepository.save(replaced);

            //update the rating field in hotel entity
            hotel.get().setRating(ratingRepository.avgRatingByHotelId(hotelId));
            hotelService.save(hotel.get());
            return RatingMapper.map(replaced);

        }
        Rating newRating = Rating.builder()
                .hotel(HotelMapper.map(hotel.get()))
                .user(UserMapper.map(user.get()))
                .rating(rating)
                .build();

        newRating = ratingRepository.save(newRating);
        hotel.get().setRating(ratingRepository.avgRatingByHotelId(hotelId));
        hotelService.save(hotel.get());
        return RatingMapper.map(newRating);
    }

    @Override
    public void deleteRatingById(long id) {
        if(getRatingById(id).isPresent()){
            HotelDto hotelDto = getRatingById(id).get().getHotel();
            hotelDto.setRating(ratingRepository.avgRatingByHotelId(hotelDto.getId()));
            hotelService.save(hotelDto);
        }
        ratingRepository.deleteById(id);
    }
}
