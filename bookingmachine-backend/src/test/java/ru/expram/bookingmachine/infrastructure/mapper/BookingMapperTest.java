package ru.expram.bookingmachine.infrastructure.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingMapperTest {

    @Mock
    private TripMapper tripMapper;

    @InjectMocks
    private BookingMapper bookingMapper;

    private TripEntity tripEntity;
    private Trip trip;

    @BeforeEach
    void setUp() {
        Route route = Route.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        RouteEntity routeEntity = RouteEntity.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        this.tripEntity = TripEntity.builder()
                .id(1L)
                .route(routeEntity)
                .departureTime(LocalDateTime.of(2025, 2, 19, 8, 30))
                .arrivalTime(LocalDateTime.of(2025, 2, 19, 12, 30))
                .maxSeats(40)
                .price(2000d)
                .build();

        this.trip = Trip.builder()
                .id(1L)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 2, 19, 8, 30))
                .arrivalTime(LocalDateTime.of(2025, 2, 19, 12, 30))
                .maxSeats(40)
                .price(2000d)
                .build();
    }

    @Test
    void mapToModel_ShouldMapCorrectly() {
        BookingEntity bookingEntity = BookingEntity.builder()
                .id(1L)
                .trip(tripEntity)
                .firstName("Andrey")
                .lastName("Vasilyev")
                .email("andrey@test.com")
                .refundCode("ABC")
                .seatNumber(10)
                .build();

        when(tripMapper.mapToModel(tripEntity)).thenReturn(trip);

        Booking booking = bookingMapper.mapToModel(bookingEntity);

        assertNotNull(booking);
        assertEquals(bookingEntity.getId(), booking.getId());
        assertEquals(booking.getTrip(), trip);
        assertEquals(bookingEntity.getFirstName(), booking.getFullName().getFirstName());
        assertEquals(bookingEntity.getLastName(), booking.getFullName().getLastName());
        assertEquals(bookingEntity.getEmail(), booking.getEmail().getValue());
        assertEquals(bookingEntity.getRefundCode(), booking.getRefundCode());
        assertEquals(bookingEntity.getSeatNumber(), booking.getSeatNumber());

        verify(tripMapper, times(1)).mapToModel(tripEntity);
    }

    @Test
    void mapToEntity_ShouldMapCorrectly() {
        Booking booking = Booking.builder()
                .id(1L)
                .trip(trip)
                .fullName(new FullName("Andrey", "Vasilyev"))
                .email(new Email("andrey@test.com"))
                .refundCode("ABC")
                .seatNumber(10)
                .build();

        when(tripMapper.mapToEntity(trip)).thenReturn(tripEntity);

        BookingEntity bookingEntity = bookingMapper.mapToEntity(booking);

        assertNotNull(bookingEntity);
        assertEquals(booking.getId(), bookingEntity.getId());
        assertEquals(bookingEntity.getTrip(), tripEntity);
        assertEquals(booking.getFullName().getFirstName(), bookingEntity.getFirstName());
        assertEquals(booking.getFullName().getLastName(), bookingEntity.getLastName());
        assertEquals(booking.getEmail().getValue(), bookingEntity.getEmail());
        assertEquals(booking.getRefundCode(), bookingEntity.getRefundCode());
        assertEquals(booking.getSeatNumber(), bookingEntity.getSeatNumber());

        verify(tripMapper, times(1)).mapToEntity(trip);
    }
}