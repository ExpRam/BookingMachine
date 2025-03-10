package ru.expram.bookingmachine.presentation.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingResponse;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;
import ru.expram.bookingmachine.application.services.IBookingService;

@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("api/booking")
public class BookingController {

    private final IBookingService bookingService;

    @PostMapping
    public BookingDTO takeBooking(@Valid @RequestBody TakeBookingRequest request) {
        return bookingService.takeBooking(request);
    }

    @DeleteMapping
    public RefundBookingResponse refundBooking(@Valid @RequestBody RefundBookingRequest request) {
        return bookingService.refundBooking(request);
    }
}
