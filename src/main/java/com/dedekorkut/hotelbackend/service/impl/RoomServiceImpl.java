package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.dto.input.NewRoomDto;
import com.dedekorkut.hotelbackend.entity.Room;
import com.dedekorkut.hotelbackend.mapper.HotelMapper;
import com.dedekorkut.hotelbackend.mapper.RoomMapper;
import com.dedekorkut.hotelbackend.repository.RoomRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import com.dedekorkut.hotelbackend.service.RoomService;
import com.dedekorkut.hotelbackend.specification.RoomFilter;
import com.dedekorkut.hotelbackend.specification.RoomSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public RoomServiceImpl(RoomRepository roomRepository, HotelService hotelService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
    }

    @Override
    public Page<RoomDto> findAll(int page, int limit, RoomFilter filter) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<Room> pages = roomRepository.findAll(RoomSpecs.filter(filter), pageable);

        return pages.map(RoomMapper::map);
    }

    @Override
    public Page<RoomDto> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<Room> pages = roomRepository.findAll(pageable);
        return pages.map(RoomMapper::map);
    }

    @Override
    public Page<RoomDto> findAllByHotelId(int page, int limit, Long hotelId) {
        Optional<HotelDto> hotel = hotelService.findById(hotelId);
        if (hotelService.findById(hotelId).isEmpty()) {
            throw new WillfulException("Hotel not found");
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<Room> pages = roomRepository.findAllByHotel(HotelMapper.map(hotel.get()), pageable);
        return pages.map(RoomMapper::map);
    }

    @Override
    public Optional<RoomDto> findById(long id) {
        return roomRepository.findById(id).map(RoomMapper::map);
    }

    @Override
    public RoomDto save(NewRoomDto newRoomDto, long hotelId) {

        if (newRoomDto.getCapacity() < 1 || newRoomDto.getBeds() < 1 || newRoomDto.getName() == null ||
                newRoomDto.getPrice() == null) {
            throw new WillfulException("Missing a field from (beds, capacity, name)");
        }

        Optional<HotelDto> hotel = hotelService.findById(hotelId);
        if (hotelService.findById(hotelId).isEmpty()) {
            throw new WillfulException("Hotel not found");
        }

        Room room = Room.builder()
                .name(newRoomDto.getName())
                .beds(newRoomDto.getBeds())
                .capacity(newRoomDto.getCapacity())
                .price(newRoomDto.getPrice())
                .build();

        room.setHotel(HotelMapper.map(hotel.get()));

        room = roomRepository.save(room);
        return RoomMapper.map(room);
    }

    @Override
    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }
}
