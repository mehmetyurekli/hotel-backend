package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.User;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAllByUserId(long userId);
    List<Reservation> getStays(User user, Hotel hotel);
    List<Reservation> getStays(long userId, long hotel);
}
