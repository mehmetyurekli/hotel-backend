package com.dedekorkut.hotelbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "internal_id", nullable = false)
    private long internalId;

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
}
