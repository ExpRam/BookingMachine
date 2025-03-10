package ru.expram.bookingmachine.domain.exceptions;

import ru.expram.bookingmachine.domain.common.BaseException;

public class InvalidEmailException extends BaseException {

    public InvalidEmailException() {
        super("Specified email is not valid!", HTTP_BAD_REQUEST);
    }
}
