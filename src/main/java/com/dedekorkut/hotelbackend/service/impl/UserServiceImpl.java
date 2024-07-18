package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.entity.User;
import com.dedekorkut.hotelbackend.mapper.UserMapper;
import com.dedekorkut.hotelbackend.repository.UserRepository;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id).map(UserMapper::map);
    }

    @Override
    public UserDto save(UserDto user) {
        User entity = UserMapper.map(user);
        entity = userRepository.save(entity);
        return UserMapper.map(entity);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
