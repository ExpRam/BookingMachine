package ru.expram.bookingmachine.application.dtos.base;

import java.time.LocalDateTime;

public record TripExtendedDTO(Long id,
                              RouteDTO route,
                              LocalDateTime departureTime,
                              LocalDateTime arrivalTime,
                              Integer maxSeats,
                              Integer availableSeatsTotal,
                              Double price) { }
