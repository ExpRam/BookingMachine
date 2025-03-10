package ru.expram.bookingmachine.application.dtos.post;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TakeBookingRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void takeBookingRequest_ShouldPassValidation_WithValidData() {
        TakeBookingRequest request = new TakeBookingRequest(
                1L, "Andrey", "Vasilyev", "andrey@test.com", 5
        );

        var violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void takeBookingRequest_ShouldFailValidation_WhenTripIdIsNull() {
        TakeBookingRequest request = new TakeBookingRequest(
                null, "Andrey", "Vasilyev", "andrey@test.com", 5
        );

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void takeBookingRequest_ShouldFailValidation_WhenFirstNameIsEmpty() {
        TakeBookingRequest request = new TakeBookingRequest(
                1L, "", "Vasilyev", "andrey@test.com", 5
        );

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void takeBookingRequest_ShouldFailValidation_WhenLastNameIsEmpty() {
        TakeBookingRequest request = new TakeBookingRequest(
                1L, "Andrey", "", "andrey@test.com", 5
        );

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void takeBookingRequest_ShouldFailValidation_WhenEmailIsEmpty() {
        TakeBookingRequest request = new TakeBookingRequest(
                1L, "Andrey", "Vasilyev", "", 5
        );

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}