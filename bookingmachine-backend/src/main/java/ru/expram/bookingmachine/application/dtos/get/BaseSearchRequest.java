package ru.expram.bookingmachine.application.dtos.get;

import ru.expram.bookingmachine.domain.enums.TransportType;

import java.time.LocalDateTime;

public record BaseSearchRequest(String departureCity,
                                String arrivalCity,
                                LocalDateTime departureTime,
                                LocalDateTime arrivalTime,
                                TransportType transportType) { }
