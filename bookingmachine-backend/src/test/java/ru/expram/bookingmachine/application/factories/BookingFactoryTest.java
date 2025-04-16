package ru.expram.bookingmachine.application.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.exceptions.InvalidEmailException;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookingFactoryTest {

    private Trip trip;
    private Set<Integer> availableSeats;
    private TakeBookingRequest request;

    @BeforeEach
    void setUp() {
        Route route = Route.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        this.trip = Trip.builder()
                .id(1L)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 2, 19, 8, 30))
                .arrivalTime(LocalDateTime.of(2025, 2, 19, 12, 30))
                .maxSeats(40)
                .price(2000d)
                .build();

        this.availableSeats = Set.of(1, 2, 3, 4, 5);
        this.request = new TakeBookingRequest(1L, "Andrey", "Vasilyev", "andrey@test.ru", 2);
    }

    @Test
    void create_ShouldCreateBooking_WhenValidRequest() {
        Booking booking = BookingFactory.create(trip, availableSeats, request);

        assertNotNull(booking);
        assertEquals("Andrey", booking.getFullName().getFirstName());
        assertEquals("Vasilyev", booking.getFullName().getLastName());
        assertEquals("andrey@test.ru", booking.getEmail().getValue());
        assertEquals(trip, booking.getTrip());
        assertTrue(availableSeats.contains(booking.getSeatNumber()));
        assertNotNull(booking.getRefundCode());
    }

    @Test
    void create_ShouldThrowInvalidEmailException_WhenEmailIsInvalid() {
        TakeBookingRequest invalidRequest = new TakeBookingRequest(1L, "Andrey", "Vasilyev", "andreytestru", 2);;

        assertThrows(InvalidEmailException.class, () -> BookingFactory.create(trip, availableSeats, invalidRequest));
    }

    @Test
    void create_ShouldAssignRandomSeat_WhenSeatNumberIsNull() {
        TakeBookingRequest request = new TakeBookingRequest(1L, "Andrey", "Vasilyev", "andrey@test.ru", null);;

        Booking booking = BookingFactory.create(trip, availableSeats, request);

        assertNotNull(booking);
        assertTrue(availableSeats.contains(booking.getSeatNumber()));
    }
}