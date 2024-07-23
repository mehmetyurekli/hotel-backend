package com.dedekorkut.hotelbackend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "beds", nullable = false)
    private int beds;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Room() {}

    public Room(Long id, Hotel hotel, String name, int beds, int capacity, BigDecimal price) {
        this.id = id;
        this.hotel = hotel;
        this.name = name;
        this.beds = beds;
        this.capacity = capacity;
        this.price = price;
    }
}
