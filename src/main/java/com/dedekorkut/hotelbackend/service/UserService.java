package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    ResponseEntity<UserDto> findById(Long id);

    ResponseEntity<UserDto> save(UserDto user);

    HttpStatus deleteById(Long id);
}
