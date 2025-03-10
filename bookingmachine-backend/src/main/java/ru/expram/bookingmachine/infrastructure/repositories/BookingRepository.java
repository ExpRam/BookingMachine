package ru.expram.bookingmachine.infrastructure.repositories;

import lombok.RequiredArgsConstructor;
import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.infrastructure.database.BookingDAO;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class BookingRepository implements IBookingRepository {

    private final BookingDAO bookingDAO;

    private final IModelEntityMapper<Booking, BookingEntity> bookingMapper;

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
    public List<Integer> findAllSeatsByTripIds(Set<Long> tripIds) {
        return bookingDAO.findAllSeatsByTripIds(tripIds);
    }

}
