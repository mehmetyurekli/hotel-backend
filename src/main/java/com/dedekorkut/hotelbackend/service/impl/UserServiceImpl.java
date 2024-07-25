package com.dedekorkut.hotelbackend.service.impl;

import com.dedekorkut.hotelbackend.common.WillfulException;
import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.entity.User;
import com.dedekorkut.hotelbackend.mapper.UserMapper;
import com.dedekorkut.hotelbackend.repository.UserRepository;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> findById(Long id) {
        if(id == null){
            throw new WillfulException("User id is null");
        }

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new WillfulException("User not found (id: " + id + ")");
        }

        UserDto userDto = UserMapper.map(optionalUser.get());

        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> save(UserDto user) {
        if (user.getFirstName() == null || user.getLastName() == null ||
                user.getEmail() == null || user.getRole() == null) {
            throw new WillfulException("Missing a field from (firstName, lastName, email, role)");
        }
        User saved = UserMapper.map(user);
        saved = userRepository.save(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.map(saved));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        findById(id);

        userRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
