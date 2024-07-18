package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.dto.RoomDto;
import com.dedekorkut.hotelbackend.entity.Room;
import com.dedekorkut.hotelbackend.mapper.RoomMapper;
import com.dedekorkut.hotelbackend.repository.RoomRepository;
import com.dedekorkut.hotelbackend.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> findAll() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDto> findById(long id) {
        return roomRepository.findById(id).map(RoomMapper::map);
    }

    @Override
    public RoomDto save(RoomDto room) {
        Room entity = RoomMapper.map(room);
        entity = roomRepository.save(entity);
        return RoomMapper.map(entity);
    }

    @Override
    public void deleteById(long id) {
        roomRepository.deleteById(id);
    }
}
