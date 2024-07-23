package com.dedekorkut.hotelbackend.service;

import com.dedekorkut.hotelbackend.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(long id);

    UserDto save(UserDto user);

    void deleteById(long id);
}
