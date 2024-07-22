package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.mapper.HotelMapper;
import com.dedekorkut.hotelbackend.repository.HotelRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelDto> findAll() {
        return hotelRepository.findAll()
                .stream()
                .map(hotel -> {
                    return HotelMapper.map(hotel);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelDto> findAllByCity(String city) {
        return hotelRepository.findByCity(city)
                .stream()
                .map(hotel -> {
                    return HotelMapper.map(hotel);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HotelDto> findById(long id) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    return HotelMapper.map(hotel);
                });
    }

    @Override
    public HotelDto save(HotelDto dto) {
        if (dto.getName() == null || dto.getAddress() == null ||
                dto.getCity() == null){
            throw new WillfulException("Missing a field from (name, address, city)");
        }
        Hotel entity = HotelMapper.map(dto);
        entity = hotelRepository.save(entity);
        return HotelMapper.map(entity);
    }

    @Override
    public void deleteById(long id) {
        hotelRepository.deleteById(id);
    }
}
