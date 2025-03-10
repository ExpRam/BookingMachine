package ru.expram.bookingmachine.application.repositories;

import ru.expram.bookingmachine.domain.models.Booking;

import java.util.List;
import java.util.Set;

public interface IBookingRepository {

    Booking save(Booking booking);
    boolean existsByRefundCode(String refundCode);
    void deleteBookingByRefundCode(String refundCode);
    boolean existsByEmailAndTripId(String email, Long tripId);
    Set<Integer> findAllOccupiedSeatsByTripId(Long tripId);
    List<Integer> findAllSeatsByTripIds(Set<Long> tripIds);
}
