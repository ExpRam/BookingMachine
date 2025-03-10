package ru.expram.bookingmachine.presentation.handlers.exceptions;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BaseSpringException {

    private final ZonedDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public BaseSpringException(int status, String error, String message, String path) {
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
