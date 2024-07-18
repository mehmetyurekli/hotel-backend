package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(long userId);

    List<Reservation> findAllByUserAndRoom_Hotel(User user, Hotel hotel);
    List<Reservation> findAllByUser_IdAndRoom_Hotel_Id(long userId, long hotelId);
}
