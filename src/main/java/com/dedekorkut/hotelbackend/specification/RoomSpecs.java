package com.dedekorkut.hotelbackend.specification;

import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Reservation;
import com.dedekorkut.hotelbackend.entity.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomSpecs {

    public static Specification<Room> filter(RoomFilter filter){

        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //selecting reservations between given date

            if(filter.getStartDate() != null && filter.getEndDate() != null){
                LocalDate start = LocalDate.parse(filter.getStartDate());
                LocalDate end = LocalDate.parse(filter.getEndDate());
                if(start.isAfter(end) || start.isEqual(end)){
                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<Reservation> reservationRoot = subquery.from(Reservation.class);
                    subquery.select(reservationRoot.get("room").get("id")).where(
                            builder.between(reservationRoot.get("date").as(LocalDate.class),
                                    LocalDate.parse(filter.getStartDate()), LocalDate.parse(filter.getEndDate()))
                    );
                    predicates.add(builder.not(root.get("id").in(subquery)));
                }
            }

            if(filter.getHotelName() != null && !filter.getHotelName().isEmpty()){
                predicates.add(builder.equal(root.get("hotelName"), filter.getHotelName()));
            }

            if(filter.getBeds() != null && filter.getBeds()>0){
                predicates.add(builder.equal(root.get("beds"), filter.getBeds()));
            }

            if(filter.getCapacity() != null && filter.getCapacity()>0){
                predicates.add(builder.equal(root.get("capacity"), filter.getCapacity()));
            }

            if(filter.getCity() != null && !filter.getCity().isEmpty()){
                Join<Room, Hotel> hotelJoin = root.join("hotel");
                predicates.add(builder.equal(hotelJoin.get("city"), filter.getCity()));
            }


            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
