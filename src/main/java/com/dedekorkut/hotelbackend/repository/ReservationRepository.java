package com.dedekorkut.hotelbackend.repository;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(long userId);

    List<Reservation> findAllByUser_IdAndRoom_Hotel_Id(long userId, long hotelId);

    Reservation findByDateAndRoomId(LocalDate date, Long roomId);


    @Query(value = "SELECT r.id FROM rooms r\n" +
            "LEFT JOIN(\n" +
            "\tSELECT room_id, date FROM reservations\n" +
            "\tWHERE date BETWEEN :start AND :end\n" +
            ") as x ON x.room_id = r.id\n" +
            "WHERE x.date IS NULL\n", nativeQuery = true)
    List<Long> findAvailableRooms(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
