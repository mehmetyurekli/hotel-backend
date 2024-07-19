package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotel(Hotel hotel);
}
