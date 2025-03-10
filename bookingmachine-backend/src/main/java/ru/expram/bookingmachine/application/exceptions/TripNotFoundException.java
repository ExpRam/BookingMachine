package ru.expram.bookingmachine.application.exceptions;

import ru.expram.bookingmachine.domain.common.BaseException;

public class TripNotFoundException extends BaseException {

    public TripNotFoundException(Long id) {
        super("Trip with id %d is not found!".formatted(id), HTTP_NOT_FOUND);
    }
}
