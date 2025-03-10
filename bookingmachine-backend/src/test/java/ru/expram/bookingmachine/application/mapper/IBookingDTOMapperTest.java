package ru.expram.bookingmachine.application.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IBookingDTOMapperTest {

    private IBookingDTOMapper bookingDTOMapper;

    @BeforeEach
    void setUp() {
        bookingDTOMapper = Mappers.getMapper(IBookingDTOMapper.class);
    }

    @Test
    void toBookingDTO_ShouldMapCorrectly() {
        FullName fullName = new FullName("Andrey", "Vasilyev");
        Email email = new Email("andrey@test.com");

        Route route = Route.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        Trip trip = Trip.builder()
                .id(1L)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 2, 19, 8, 30))
                .arrivalTime(LocalDateTime.of(2025, 2, 19, 12, 30))
                .maxSeats(40)
                .price(1200.50)
                .build();

        Booking booking = Booking.builder()
                .id(1L)
                .fullName(fullName)
                .email(email)
                .trip(trip)
                .seatNumber(5)
                .build();

        BookingDTO bookingDTO = bookingDTOMapper.toBookingDTO(booking);

        assertNotNull(bookingDTO);
        assertEquals(booking.getFullName().getFirstName(), bookingDTO.firstName());
        assertEquals(booking.getFullName().getLastName(), bookingDTO.lastName());
        assertEquals(booking.getEmail().getValue(), bookingDTO.email());
        assertNotNull(bookingDTO.trip());
        assertEquals(booking.getTrip().getId(), bookingDTO.trip().id());
        assertEquals(booking.getTrip().getRoute().getDepartureCity(), bookingDTO.trip().route().departureCity());
        assertEquals(booking.getTrip().getRoute().getArrivalCity(), bookingDTO.trip().route().arrivalCity());
        assertEquals(booking.getTrip().getRoute().getTransportType().name(), bookingDTO.trip().route().transportType());
        assertEquals(booking.getTrip().getDepartureTime(), bookingDTO.trip().departureTime());
        assertEquals(booking.getTrip().getArrivalTime(), bookingDTO.trip().arrivalTime());
        assertEquals(booking.getTrip().getMaxSeats(), bookingDTO.trip().maxSeats());
        assertEquals(booking.getTrip().getPrice(), bookingDTO.trip().price());
    }
}
