package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.User;
import com.dedekorkut.hotelbackend.repository.ReservationRepository;
import com.dedekorkut.hotelbackend.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllByUserId(long userId) {
        return reservationRepository.findAllByUserId(userId);
    }

    @Override
    public List<Reservation> getStays(User user, Hotel hotel) {
        return reservationRepository.findAllByUserAndRoom_Hotel(user, hotel);
    }

    @Override
    public List<Reservation> getStays(long userId, long hotel) {
        return reservationRepository.findAllByUser_IdAndRoom_Hotel_Id(userId, hotel);
    }
}
