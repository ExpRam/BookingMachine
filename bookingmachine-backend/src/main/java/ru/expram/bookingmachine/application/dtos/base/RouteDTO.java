package ru.expram.bookingmachine.application.dtos.base;

public record RouteDTO(String departureCity,
                       String arrivalCity,
                       String transportType) { }
