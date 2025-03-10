package ru.expram.bookingmachine.application.dtos.base;

import java.time.LocalDateTime;

public record TripDTO(Long id,
                      RouteDTO route,
                      LocalDateTime departureTime,
                      LocalDateTime arrivalTime,
                      Integer maxSeats,
                      Double price) { }
