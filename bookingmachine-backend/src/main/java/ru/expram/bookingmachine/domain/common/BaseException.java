package ru.expram.bookingmachine.domain.common;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_CONFLICT = 409;

    private final int errorCode;

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
