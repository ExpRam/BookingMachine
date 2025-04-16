package ru.expram.bookingmachine.application.factories;

import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;

import java.util.Set;

public class BookingFactory {

    // Build Booking aggregate after validation
    public static Booking create(Trip trip, Set<Integer> availableSeats, TakeBookingRequest request) {
        final String email = request.email();
        final Integer seatNumber = request.seatNumber();

        final FullName fullNameVO = new FullName(request.firstName(), request.lastName());
        final Email emailVO = new Email(email);

        final Booking booking = Booking.builder()
                .trip(trip)
                .fullName(fullNameVO)
                .email(emailVO)
                .build();

        booking.setRandomOrReadySeat(availableSeats, seatNumber);
        booking.generateRefundCode();

        return booking;
    }
}
