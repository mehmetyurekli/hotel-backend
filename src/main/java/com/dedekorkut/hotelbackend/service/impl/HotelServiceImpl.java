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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HotelDto> findById(Long id) {

        if(id == null){
            throw new WillfulException("Hotel id is null");
        }
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if(optionalHotel.isEmpty()){
            throw new WillfulException("Hotel not found (id: " + id + ")");
        }

        HotelDto hotelDto = HotelMapper.map(optionalHotel.get());

        return ResponseEntity.ok(hotelDto);
    }

    @Override
    public ResponseEntity<HotelDto> save(HotelDto dto) {
        if (dto.getName() == null || dto.getAddress() == null ||
                dto.getCity() == null) {
            throw new WillfulException("Missing a field from (name, address, city)");
        }
        Hotel saved = HotelMapper.convertToEntity(dto);
        saved = hotelRepository.save(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(HotelMapper.map(saved));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        findById(id);

        hotelRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
