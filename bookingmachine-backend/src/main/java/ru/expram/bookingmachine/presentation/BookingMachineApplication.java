package ru.expram.bookingmachine.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookingMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingMachineApplication.class, args);
	}

}
