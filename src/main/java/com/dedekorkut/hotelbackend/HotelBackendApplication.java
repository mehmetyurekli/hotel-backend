package com.dedekorkut.hotelbackend;

import com.dedekorkut.hotelbackend.repository.UserRepository;
import com.dedekorkut.hotelbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBackendApplication.class, args);
	}

}
