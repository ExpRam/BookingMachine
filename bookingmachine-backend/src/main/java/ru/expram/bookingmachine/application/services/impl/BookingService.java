package ru.expram.bookingmachine.application.services.impl;

import lombok.AllArgsConstructor;
import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingResponse;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;
import ru.expram.bookingmachine.application.exceptions.EmailAlreadyOnTripException;
import ru.expram.bookingmachine.application.exceptions.TripNotFoundException;
import ru.expram.bookingmachine.application.factories.BookingFactory;
import ru.expram.bookingmachine.application.mapper.IBookingDTOMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.application.services.IBookingService;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Trip;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class BookingService implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final ITripRepository tripRepository;

    private final IBookingDTOMapper dtoMapper;

    @Override
    public BookingDTO takeBooking(TakeBookingRequest request) {
        final String email = request.email();
        final Long tripId = request.tripId();

        final Optional<Trip> tripOptional = tripRepository.findTripById(tripId);

        if(tripOptional.isEmpty())
            throw new TripNotFoundException(tripId);

        if(bookingRepository.existsByEmailAndTripId(email, tripId)) {
            throw new EmailAlreadyOnTripException();
        }

        final Trip trip = tripOptional.get();

        final Set<Integer> availableSeats = trip.getAvailableSeats(bookingRepository.findAllOccupiedSeatsByTripId(trip.getId()));

        final Booking booking = BookingFactory.create(trip, availableSeats, request);

        return dtoMapper.toBookingDTO(bookingRepository.save(booking));
    }

    @Override
    public RefundBookingResponse refundBooking(RefundBookingRequest request) {
        final String refundCode = request.refundCode();

        if(!bookingRepository.existsByRefundCode(refundCode))
            return new RefundBookingResponse(false);

        bookingRepository.deleteBookingByRefundCode(refundCode);

        return new RefundBookingResponse(true);
    }
}
