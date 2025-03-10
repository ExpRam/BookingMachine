package ru.expram.bookingmachine.application.services;

import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingResponse;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;

public interface IBookingService {

    BookingDTO takeBooking(TakeBookingRequest request);
    RefundBookingResponse refundBooking(RefundBookingRequest request);
}
