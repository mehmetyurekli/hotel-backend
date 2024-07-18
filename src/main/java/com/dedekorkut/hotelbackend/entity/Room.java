package com.dedekorkut.hotelbackend.entity;

import jakarta.persistence.*;
import lombok.Builder;

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

    public Room() {}

    public Room(Long id, Hotel hotel, String name, int beds, int capacity) {
        this.id = id;
        this.hotel = hotel;
        this.name = name;
        this.beds = beds;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
