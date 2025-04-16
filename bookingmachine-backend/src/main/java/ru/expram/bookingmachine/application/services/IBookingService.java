package ru.expram.bookingmachine.application.services;

import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingResponse;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;

import java.util.concurrent.CompletableFuture;

public interface IBookingService {

    CompletableFuture<BookingDTO> takeBooking(TakeBookingRequest request);
    CompletableFuture<RefundBookingResponse> refundBooking(RefundBookingRequest request);
}
