package ru.expram.bookingmachine.application.exceptions;

import org.springframework.http.HttpStatus;
import ru.expram.bookingmachine.domain.common.BaseException;

public class NoSeatsForTripException extends BaseException {

    public NoSeatsForTripException(Long id) {
        super("There is not available seats for this trip with id %d".formatted(id), HttpStatus.CONFLICT);
    }
}
