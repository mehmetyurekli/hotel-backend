package com.dedekorkut.hotelbackend.controller;

import com.dedekorkut.hotelbackend.dto.UserDto;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            return new ResponseEntity<>(userService.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        if(userDto.getFirstName() == null || userDto.getLastName() == null ||
                userDto.getEmail() == null || userDto.getRole() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDto saved = userService.save(userDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }
}
