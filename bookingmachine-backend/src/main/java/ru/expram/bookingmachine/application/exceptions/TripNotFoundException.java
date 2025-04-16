package ru.expram.bookingmachine.application.exceptions;

import org.springframework.http.HttpStatus;
import ru.expram.bookingmachine.domain.common.BaseException;

public class TripNotFoundException extends BaseException {

    public TripNotFoundException(Long id) {
        super("Trip with id %d is not found!".formatted(id), HttpStatus.NOT_FOUND);
    }
}
