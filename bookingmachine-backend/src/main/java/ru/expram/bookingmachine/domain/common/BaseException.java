package ru.expram.bookingmachine.domain.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int errorCode;

    public BaseException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode.value();
    }
}
