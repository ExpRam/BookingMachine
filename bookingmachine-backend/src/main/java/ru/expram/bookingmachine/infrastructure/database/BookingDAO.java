package ru.expram.bookingmachine.infrastructure.database;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;

import java.util.List;
import java.util.Set;

public interface BookingDAO extends JpaRepository<BookingEntity, Long> {

    boolean existsByEmailAndTripId(String email, Long tripId);
    boolean existsByRefundCode(String refundCode);

    @Query("SELECT b.seatNumber FROM BookingEntity b WHERE b.trip.id = :tripId")
    Set<Integer> findAllSeatsByTripId(Long tripId);

    @Query("SELECT (SELECT COUNT(b.seatNumber) FROM BookingEntity b WHERE b.trip.id = t.id) AS seatCount, t.id FROM TripEntity t WHERE t.id IN :tripIds ORDER BY t.departureTime")
    List<Integer> findAllSeatsByTripIds(Iterable<Long> tripIds);

    @Transactional
    void deleteByRefundCode(String refundCode);
}
