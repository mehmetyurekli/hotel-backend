package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.dto.NewRatingDto;
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
    public List<RatingDto> findAllByUserId(long userId) {
        if(userService.findById(userId).isEmpty()){
            throw new WillfulException("User not found");
        }
        return ratingRepository.findAllByUserId(userId)
                .stream()
                .map(RatingMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingDto> findAllByHotelId(long hotelId) {
        if(hotelService.findById(hotelId).isEmpty()){
            throw new WillfulException("Hotel not found");
        }
        return ratingRepository.findAllByHotelId(hotelId)
                .stream()
                .map(RatingMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RatingDto> getRatingById(long id) {
        return ratingRepository.findById(id).map(RatingMapper::map);
    }

    @Override
    public RatingDto addRating(NewRatingDto newRatingDto){
        Optional<UserDto> user = userService.findById(newRatingDto.getUserId());
        Optional<HotelDto> hotel = hotelService.findById(newRatingDto.getHotelId());

        if(hotel.isEmpty() || user.isEmpty()) {
            throw new WillfulException("Hotel or User not found.");
        }
        if(reservationService.getStays(newRatingDto.getHotelId(), newRatingDto.getUserId()).isEmpty()) {
            throw new WillfulException("No reservation found."); //no reservation, cant review hotel
        }
        if(newRatingDto.getRating() < 0 || newRatingDto.getRating() > 10) {
            throw new WillfulException("Rating must be between 0 and 10.");
        }

        Optional<Rating> existing = ratingRepository.findRatingByHotelIdAndUserId(newRatingDto.getHotelId(), newRatingDto.getUserId());
        Rating existingOrNewRating;
        if(existing.isPresent()) {
            existingOrNewRating = existing.get();
            existingOrNewRating.setRating(newRatingDto.getRating());
            existingOrNewRating = ratingRepository.save(existingOrNewRating);
        }
        else{
            existingOrNewRating = Rating.builder()
                    .hotel(HotelMapper.map(hotel.get()))
                    .user(UserMapper.map(user.get()))
                    .rating(newRatingDto.getRating())
                    .build();

            existingOrNewRating = ratingRepository.save(existingOrNewRating);
        }

        //update the hotel's rating field
        hotel.get().setRating(ratingRepository.avgRatingByHotelId(newRatingDto.getHotelId()));
        hotelService.save(hotel.get());
        return RatingMapper.map(existingOrNewRating);
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
