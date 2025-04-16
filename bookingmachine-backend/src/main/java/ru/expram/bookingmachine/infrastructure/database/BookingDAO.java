package ru.expram.bookingmachine.infrastructure.database;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.expram.bookingmachine.infrastructure.database.projections.TripWithOccupiedSeatsProjection;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;
import ru.expram.bookingmachine.utils.SortSettings;

import java.util.List;
import java.util.Set;

public interface BookingDAO extends JpaRepository<BookingEntity, Long> {

    boolean existsByEmailAndTripId(String email, Long tripId);
    boolean existsByRefundCode(String refundCode);

    @Query("SELECT b.seatNumber FROM BookingEntity b WHERE b.trip.id = :tripId")
    Set<Integer> findAllSeatsByTripId(Long tripId);

    @Query("""
    SELECT
        COUNT(b.seatNumber) AS occupiedSeatsCount,
        t.id AS tripId
    FROM BookingEntity b
    INNER JOIN b.trip t
    WHERE t.id IN :tripIds
    GROUP BY t.id
    """ + SortSettings.SORT_QUERY)
    List<TripWithOccupiedSeatsProjection> findAllSeatsByTripIds(Iterable<Long> tripIds);

    @Transactional
    void deleteByRefundCode(String refundCode);
}
