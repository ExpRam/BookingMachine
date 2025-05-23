package ru.expram.bookingmachine.application.repositories;

import ru.expram.bookingmachine.domain.models.Booking;

import java.util.Map;
import java.util.Set;

public interface IBookingRepository {

    Booking save(Booking booking);
    boolean existsByRefundCode(String refundCode);
    void deleteBookingByRefundCode(String refundCode);
    boolean existsByEmailAndTripId(String email, Long tripId);
    Set<Integer> findAllOccupiedSeatsByTripId(Long tripId);
    Map<Long, Integer> findAllSeatsByTripIds(Iterable<Long> tripIds);
}
