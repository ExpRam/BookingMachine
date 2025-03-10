package ru.expram.bookingmachine.infrastructure.mapper;

import lombok.AllArgsConstructor;
import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

@AllArgsConstructor
public class BookingMapper implements IModelEntityMapper<Booking, BookingEntity> {

    private final IModelEntityMapper<Trip, TripEntity> tripMapper;

    @Override
    public Booking mapToModel(BookingEntity entity) {
        final Email email = new Email(entity.getEmail());
        final FullName fullName = new FullName(entity.getFirstName(), entity.getLastName());

        return Booking.builder()
                .id(entity.getId())
                .trip(tripMapper.mapToModel(entity.getTrip()))
                .fullName(fullName)
                .email(email)
                .seatNumber(entity.getSeatNumber())
                .refundCode(entity.getRefundCode()).build();
    }

    @Override
    public BookingEntity mapToEntity(Booking model) {
        return BookingEntity.builder()
                .id(model.getId())
                .trip(tripMapper.mapToEntity(model.getTrip()))
                .firstName(model.getFullName().getFirstName())
                .lastName(model.getFullName().getLastName())
                .email(model.getEmail().getValue())
                .seatNumber(model.getSeatNumber())
                .refundCode(model.getRefundCode())
                .build();
    }
}
