package ru.expram.bookingmachine.application.exceptions;

import ru.expram.bookingmachine.domain.common.BaseException;

public class EmailAlreadyOnTripException extends BaseException {

    public EmailAlreadyOnTripException() {
        super("This email is already on the trip!", HTTP_BAD_REQUEST);
    }
}
