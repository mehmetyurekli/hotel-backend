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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Page<RoomDto> findAll(int page, int size, RoomFilter filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Room> pages = roomRepository.findAll(RoomSpecs.filter(filter), pageable);

        return pages.map(RoomMapper::map);
    }

    @Override
    public Page<RoomDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Room> pages = roomRepository.findAll(pageable);
        return pages.map(RoomMapper::map);
    }

    @Override
    public Page<RoomDto> findAllByHotelId(int page, int size, Long hotelId) {
        HotelDto hotel = hotelService.findById(hotelId).getBody();

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        assert hotel != null;
        Page<Room> pages = roomRepository.findAllByHotel(HotelMapper.convertToEntity(hotel), pageable);
        return pages.map(RoomMapper::map);
    }

    @Override
    public ResponseEntity<RoomDto> findById(Long id) {
        if(id == null){
            throw new WillfulException("Room id is null");
        }

        Optional<Room> room = roomRepository.findById(id);

        if(room.isEmpty()){
            throw new WillfulException("Room not found (id: " + id + ")");
        }

        RoomDto roomDto = RoomMapper.map(room.get());

        return ResponseEntity.ok(roomDto);
    }

    @Override
    public ResponseEntity<RoomDto> save(NewRoomDto newRoomDto, Long hotelId) {

        if (newRoomDto.getCapacity() < 1 || newRoomDto.getBeds() < 1 || newRoomDto.getName() == null ||
                newRoomDto.getPrice() == null) {
            throw new WillfulException("Missing a field from (beds, capacity, name)");
        }

        HotelDto hotel = hotelService.findById(hotelId).getBody();

        Room room = Room.builder()
                .name(newRoomDto.getName())
                .beds(newRoomDto.getBeds())
                .capacity(newRoomDto.getCapacity())
                .price(newRoomDto.getPrice())
                .build();

        assert hotel != null;
        room.setHotel(HotelMapper.convertToEntity(hotel));

        room = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomMapper.map(room));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        findById(id);

        roomRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
