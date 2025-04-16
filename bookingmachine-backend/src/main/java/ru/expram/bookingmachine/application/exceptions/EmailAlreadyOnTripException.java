package ru.expram.bookingmachine.application.exceptions;

import org.springframework.http.HttpStatus;
import ru.expram.bookingmachine.domain.common.BaseException;

public class EmailAlreadyOnTripException extends BaseException {

    public EmailAlreadyOnTripException() {
        super("This email is already on the trip!", HttpStatus.BAD_REQUEST);
    }
}
