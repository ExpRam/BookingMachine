package ru.expram.bookingmachine.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import ru.expram.bookingmachine.domain.exceptions.InvalidEmailException;

@Data
public class Email {

    // RFC-legal pattern
    @Getter(AccessLevel.NONE)
    private static final String EMAIL_PATTERN = ".+@.+\\..+";

    private final String value;

    public Email(String value) {
        this.value = value;
        validate();
    }

    private void validate() {
        if(!value.matches(EMAIL_PATTERN))
            throw new InvalidEmailException();
    }
}
