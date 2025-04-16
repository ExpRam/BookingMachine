package ru.expram.bookingmachine.application.services.impl;

import lombok.AllArgsConstructor;
import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingResponse;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;
import ru.expram.bookingmachine.application.exceptions.EmailAlreadyOnTripException;
import ru.expram.bookingmachine.application.exceptions.NoSeatsForTripException;
import ru.expram.bookingmachine.application.exceptions.SeatAlreadyTakenException;
import ru.expram.bookingmachine.application.exceptions.TripNotFoundException;
import ru.expram.bookingmachine.application.factories.BookingFactory;
import ru.expram.bookingmachine.application.mapper.IBookingDTOMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.application.services.IBookingService;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Trip;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class BookingService implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final ITripRepository tripRepository;

    private final IBookingDTOMapper dtoMapper;

    @Override
    public CompletableFuture<BookingDTO> takeBooking(TakeBookingRequest request) {
        final String email = request.email();
        final Long tripId = request.tripId();
        final Integer seatNumber = request.seatNumber();

        final Trip trip = tripRepository.findTripById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId));

        if(bookingRepository.existsByEmailAndTripId(email, tripId)) {
            throw new EmailAlreadyOnTripException();
        }

        // Collecting all available seats and then validating everything related to it
        final Set<Integer> availableSeats = trip.getAvailableSeats(
                bookingRepository.findAllOccupiedSeatsByTripId(trip.getId())
        );

        if((trip.getMaxSeats() - availableSeats.size()) >= trip.getMaxSeats())
            throw new NoSeatsForTripException(tripId);

        if(seatNumber != null && !availableSeats.contains(seatNumber))
            throw new SeatAlreadyTakenException(tripId);

        final Booking booking = BookingFactory.create(trip, availableSeats, request);
        final Booking savedBooking = bookingRepository.save(booking);

        return CompletableFuture.completedFuture(
                dtoMapper.toBookingDTO(savedBooking)
        );
    }

    @Override
    public CompletableFuture<RefundBookingResponse> refundBooking(RefundBookingRequest request) {
        final String refundCode = request.refundCode();
        // Searching by refundCode
        final boolean bookingExists = bookingRepository.existsByRefundCode(refundCode);

        if (bookingExists) {
            bookingRepository.deleteBookingByRefundCode(refundCode);
        }

        return CompletableFuture.completedFuture(new RefundBookingResponse(bookingExists));
    }
}
