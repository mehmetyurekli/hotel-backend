package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.HotelDto;
import com.dedekorkut.hotelbackend.entity.Hotel;
import com.dedekorkut.hotelbackend.mapper.HotelMapper;
import com.dedekorkut.hotelbackend.repository.HotelRepository;
import com.dedekorkut.hotelbackend.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Page<HotelDto> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Hotel> pages = hotelRepository.findAll(pageable);

        return pages.map(HotelMapper::map);
    }

    @Override
    public Optional<HotelDto> findById(long id) {
        return hotelRepository.findById(id)
                .map(HotelMapper::map);
    }

    @Override
    public HotelDto save(HotelDto dto) {
        if (dto.getName() == null || dto.getAddress() == null ||
                dto.getCity() == null) {
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
