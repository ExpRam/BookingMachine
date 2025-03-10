package ru.expram.bookingmachine.application.exceptions;

import ru.expram.bookingmachine.domain.common.BaseException;

public class SeatAlreadyTakenException extends BaseException {

    public SeatAlreadyTakenException(Long id) {
        super("This seat is already taken for trip with id %d".formatted(id), HTTP_CONFLICT);
    }
}
