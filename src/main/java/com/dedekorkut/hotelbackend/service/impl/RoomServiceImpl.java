package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.entity.Room;
import com.dedekorkut.hotelbackend.mapper.HotelMapper;
import com.dedekorkut.hotelbackend.mapper.RoomMapper;
import com.dedekorkut.hotelbackend.repository.RoomRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import com.dedekorkut.hotelbackend.service.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public RoomServiceImpl(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    @Override
    public List<RoomDto> findAll() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> findAllByHotelId(Long hotelId) {
        Optional<HotelDto> hotel = hotelService.findById(hotelId);
        if(hotelService.findById(hotelId).isEmpty()) {
            throw new WillfulException("Hotel not found");
        }
        return roomRepository.findAllByHotel(HotelMapper.map(hotel.get()))
                .stream()
                .map(RoomMapper::mapWithoutHotel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDto> findById(long id) {
        return roomRepository.findById(id).map(RoomMapper::map);
    }

    @Override
    public RoomDto save(RoomDto room, long hotelId) {
        Optional<HotelDto> hotel = hotelService.findById(hotelId);
        if(hotelService.findById(hotelId).isEmpty()) {
            throw new WillfulException("Hotel not found");
        }
        room.setHotel(hotel.get());
        Room entity = RoomMapper.map(room);
        entity = roomRepository.save(entity);
        return RoomMapper.map(entity);
    }

    @Override
    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }
}
