package ru.expram.bookingmachine.application.dtos.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TakeBookingRequest(@NotNull Long tripId,
                                 @NotNull @NotEmpty String firstName,
                                 @NotNull @NotEmpty String lastName,
                                 @NotNull @NotEmpty String email,
                                 Integer seatNumber /* Assign random seat if null */
                                 ) { }
