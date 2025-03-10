package ru.expram.bookingmachine.presentation.handlers.exceptions;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class BaseListedSpringException {

    private final ZonedDateTime timestamp;
    private final int status;
    private final List<String> errors;
    private final String path;

    public BaseListedSpringException(int status, List<String> errors, String path) {
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.errors = errors;
        this.path = path;
    }
}
