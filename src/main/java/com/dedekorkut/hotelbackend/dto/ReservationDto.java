package com.dedekorkut.hotelbackend.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ReservationDto {

    private long id;
    private UserDto user;
    private RoomDto room;

    private LocalDate date;

    private LocalDate createdAt;

    public ReservationDto() {}

    public ReservationDto(long id, UserDto user, RoomDto room, LocalDate date, LocalDate createdAt) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.date = date;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
