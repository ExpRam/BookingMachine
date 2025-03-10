package ru.expram.bookingmachine.application.dtos.base;


public record BookingDTO(TripDTO trip,
                         String firstName,
                         String lastName,
                         String email,
                         Integer seatNumber,
                         String refundCode) { }
