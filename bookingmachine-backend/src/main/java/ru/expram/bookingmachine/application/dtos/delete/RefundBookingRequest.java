package ru.expram.bookingmachine.application.dtos.delete;

import jakarta.validation.constraints.NotNull;

public record RefundBookingRequest(@NotNull String refundCode) { }
