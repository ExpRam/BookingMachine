package ru.expram.bookingmachine.infrastructure.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;
import ru.expram.bookingmachine.infrastructure.database.BookingDAO;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingRepositoryTest {

    @Mock
    private BookingDAO bookingDAO;

    @Mock
    private IModelEntityMapper<Booking, BookingEntity> bookingMapper;

    @InjectMocks
    private BookingRepository bookingRepository;

    private Booking booking;
    private BookingEntity bookingEntity;

    @BeforeEach
    void setUp() {
        Route route = Route.builder()
                .id(1L)
                .departureCity("Омск")
                .arrivalCity("Новосибирск")
                .transportType(TransportType.AIRLINE)
                .build();

        RouteEntity routeEntity = RouteEntity.builder()
                .id(1L)
                .departureCity("Омск")
                .arrivalCity("Новосибирск")
                .transportType(TransportType.AIRLINE)
                .build();

        Trip trip = Trip.builder()
                .id(1L)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 1, 15, 13, 0))
                .arrivalTime(LocalDateTime.of(2025, 1, 15, 17, 0))
                .maxSeats(40)
                .price(10000d)
                .build();

        TripEntity tripEntity = TripEntity.builder()
                .id(1L)
                .route(routeEntity)
                .departureTime(LocalDateTime.of(2025, 1, 15, 13, 0))
                .arrivalTime(LocalDateTime.of(2025, 1, 15, 17, 0))
                .maxSeats(40)
                .price(10000d)
                .build();


        this.booking = Booking.builder()
                .id(1L)
                .trip(trip)
                .email(new Email("andrey@test.com"))
                .fullName(new FullName("Andrey", "Vasilyev"))
                .seatNumber(20)
                .refundCode("ABC")
                .build();

        this.bookingEntity = BookingEntity.builder()
                .id(1L)
                .trip(tripEntity)
                .email("andrey@test.com")
                .firstName("Andrey")
                .lastName("Vasilyev")
                .seatNumber(20)
                .refundCode("ABC")
                .build();

    }

    @Test
    void save_ShouldSaveAndReturnBooking() {
        when(bookingMapper.mapToEntity(booking)).thenReturn(bookingEntity);
        when(bookingDAO.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingMapper.mapToModel(bookingEntity)).thenReturn(booking);

        Booking result = bookingRepository.save(booking);

        assertNotNull(result);
        assertEquals(booking, result);
        verify(bookingDAO, times(1)).save(bookingEntity);
        verify(bookingMapper, times(1)).mapToEntity(booking);
        verify(bookingMapper, times(1)).mapToModel(bookingEntity);
    }

    @Test
    void existsByRefundCode_ShouldReturnTrue_WhenRefundCodeExists() {
        when(bookingDAO.existsByRefundCode("ABC")).thenReturn(true);

        boolean exists = bookingRepository.existsByRefundCode("ABC");

        assertTrue(exists);
        verify(bookingDAO, times(1)).existsByRefundCode("ABC");
    }

    @Test
    void existsByRefundCode_ShouldReturnFalse_WhenRefundCodeDoesNotExist() {
        when(bookingDAO.existsByRefundCode("INVALID_CODE")).thenReturn(false);

        boolean exists = bookingRepository.existsByRefundCode("INVALID_CODE");

        assertFalse(exists);
        verify(bookingDAO, times(1)).existsByRefundCode("INVALID_CODE");
    }

    @Test
    void deleteBookingByRefundCode_ShouldDeleteBooking() {
        doNothing().when(bookingDAO).deleteByRefundCode("ABC");

        bookingRepository.deleteBookingByRefundCode("ABC");

        verify(bookingDAO, times(1)).deleteByRefundCode("ABC");
    }

    @Test
    void existsByEmailAndTripId_ShouldReturnTrue_WhenBookingExists() {
        when(bookingDAO.existsByEmailAndTripId("andrey@test.com", 1L)).thenReturn(true);

        boolean exists = bookingRepository.existsByEmailAndTripId("andrey@test.com", 1L);

        assertTrue(exists);
        verify(bookingDAO, times(1)).existsByEmailAndTripId("andrey@test.com", 1L);
    }

    @Test
    void existsByEmailAndTripId_ShouldReturnFalse_WhenBookingDoesNotExist() {
        when(bookingDAO.existsByEmailAndTripId("andrey2@test.com", 1L)).thenReturn(false);

        boolean exists = bookingRepository.existsByEmailAndTripId("andrey2@test.com", 1L);

        assertFalse(exists);
        verify(bookingDAO, times(1)).existsByEmailAndTripId("andrey2@test.com", 1L);
    }

    @Test
    void findAllOccupiedSeatsByTripId_ShouldReturnOccupiedSeats() {
        when(bookingDAO.findAllSeatsByTripId(1L)).thenReturn(Set.of(1, 2, 3, 5));

        Set<Integer> occupiedSeats = bookingRepository.findAllOccupiedSeatsByTripId(1L);

        assertNotNull(occupiedSeats);
        assertEquals(4, occupiedSeats.size());
        assertTrue(occupiedSeats.contains(1));
        assertTrue(occupiedSeats.contains(2));
        assertTrue(occupiedSeats.contains(3));
        assertTrue(occupiedSeats.contains(5));
        verify(bookingDAO, times(1)).findAllSeatsByTripId(1L);
    }

    @Test
    void findAllSeatsByTripIds_ShouldReturnCountOfOccupiedSeatsForEveryId() {
        when(bookingDAO.findAllSeatsByTripIds(Set.of(1L))).thenReturn(List.of(1));

        List<Integer> occupiedSeats = bookingRepository.findAllSeatsByTripIds(Set.of(1L));

        assertNotNull(occupiedSeats);
        assertEquals(1, occupiedSeats.size());
        assertTrue(occupiedSeats.contains(1));
        verify(bookingDAO, times(1)).findAllSeatsByTripIds(Set.of(1L));
    }
}