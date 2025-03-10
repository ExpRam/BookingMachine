package ru.expram.bookingmachine.application.dtos.delete;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefundBookingRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void refundBookingRequest_ShouldPassValidation_WithValidData() {
        RefundBookingRequest request = new RefundBookingRequest("ABC123");

        var violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void refundBookingRequest_ShouldFailValidation_WhenRefundCodeIsNull() {
        RefundBookingRequest request = new RefundBookingRequest(null);

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}