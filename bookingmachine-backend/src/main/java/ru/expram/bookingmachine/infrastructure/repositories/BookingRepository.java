package ru.expram.bookingmachine.infrastructure.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.infrastructure.database.BookingDAO;
import ru.expram.bookingmachine.infrastructure.database.projections.TripWithOccupiedSeatsProjection;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;
import ru.expram.bookingmachine.infrastructure.mapper.BookingMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookingRepository implements IBookingRepository {

    private final BookingDAO bookingDAO;

    private final BookingMapper bookingMapper;

    @Override
    public Booking save(Booking booking) {
        final BookingEntity bookingEntity = bookingMapper.mapToEntity(booking);

        bookingDAO.save(bookingEntity);
        return bookingMapper.mapToModel(bookingEntity);
    }

    @Override
    public boolean existsByRefundCode(String refundCode) {
        return bookingDAO.existsByRefundCode(refundCode);
    }

    @Override
    public void deleteBookingByRefundCode(String refundCode) {
        bookingDAO.deleteByRefundCode(refundCode);
    }

    @Override
    public boolean existsByEmailAndTripId(String email, Long tripId) {
        return bookingDAO.existsByEmailAndTripId(email, tripId);
    }

    @Override
    public Set<Integer> findAllOccupiedSeatsByTripId(Long tripId) {
        return bookingDAO.findAllSeatsByTripId(tripId);
    }

    @Override
    @Cacheable(value = "seats", key = "#tripIds")
    public Map<Long, Integer> findAllSeatsByTripIds(Iterable<Long> tripIds) {
        // Convert projection to map
        return bookingDAO.findAllSeatsByTripIds(tripIds)
                .stream()
                .collect(Collectors.toMap(
                        TripWithOccupiedSeatsProjection::getTripId,
                        TripWithOccupiedSeatsProjection::getOccupiedSeatsCount
                ));
    }

}
