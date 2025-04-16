package ru.expram.bookingmachine.domain.exceptions;

import org.springframework.http.HttpStatus;
import ru.expram.bookingmachine.domain.common.BaseException;

public class InvalidEmailException extends BaseException {

    public InvalidEmailException() {
        super("Specified email is not valid!", HttpStatus.BAD_REQUEST);
    }
}
