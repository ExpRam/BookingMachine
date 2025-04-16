package ru.expram.bookingmachine.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.application.dtos.base.RouteDTO;
import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingResponse;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;
import ru.expram.bookingmachine.application.exceptions.EmailAlreadyOnTripException;
import ru.expram.bookingmachine.application.exceptions.TripNotFoundException;
import ru.expram.bookingmachine.application.mapper.IBookingDTOMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.application.services.impl.BookingService;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private ITripRepository tripRepository;

    @Mock
    private IBookingDTOMapper dtoMapper;

    @InjectMocks
    private BookingService bookingService;

    private Trip trip;
    private Booking booking;
    private BookingDTO bookingDTO;

    @BeforeEach
    void setUp() {
        Route route = Route.builder()
                .id(1L)
                .departureCity("Омск")
                .arrivalCity("Новосибирск")
                .transportType(TransportType.AIRLINE)
                .build();

        RouteDTO routeDTO = new RouteDTO(route.getDepartureCity(), route.getArrivalCity(), route.getTransportType().toString());

        this.trip = Trip.builder()
                .id(1L)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 1, 15, 13, 0))
                .arrivalTime(LocalDateTime.of(2025, 1, 15, 17, 0))
                .maxSeats(40)
                .price(10000d)
                .build();

        TripDTO tripDTO = new TripDTO(trip.getId(), routeDTO, trip.getDepartureTime(), trip.getArrivalTime(),
                trip.getMaxSeats(), trip.getPrice());

        this.booking = Booking.builder()
                .id(1L)
                .trip(trip)
                .email(new Email("andrey@test.com"))
                .fullName(new FullName("Andrey", "Vasilyev"))
                .seatNumber(20)
                .refundCode("ABC")
                .build();

        this.bookingDTO = new BookingDTO(tripDTO, booking.getFullName().getFirstName(),
                booking.getFullName().getLastName(), booking.getEmail().getValue(), booking.getSeatNumber(),
                booking.getRefundCode());
    }

    @Test
    void takeBooking_ShouldCreateBooking_WhenValidRequest() throws ExecutionException, InterruptedException {
        TakeBookingRequest request = new TakeBookingRequest(1L, "Andrey", "Vasilyev", "andrey@test.com", 20);

        when(tripRepository.findTripById(1L)).thenReturn(Optional.of(trip));
        when(bookingRepository.existsByEmailAndTripId("andrey@test.com", 1L)).thenReturn(false);
        when(bookingRepository.findAllOccupiedSeatsByTripId(1L)).thenReturn(Set.of(1, 2, 3, 4));
        when(bookingRepository.save(any())).thenReturn(booking);
        when(dtoMapper.toBookingDTO(booking)).thenReturn(bookingDTO);

        CompletableFuture<BookingDTO> resultFuture = bookingService.takeBooking(request);
        BookingDTO result = resultFuture.get();

        assertNotNull(result);
        assertEquals(bookingDTO, result);
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void takeBooking_ShouldThrowException_WhenEmailAlreadyOnTrip() {
        TakeBookingRequest request = new TakeBookingRequest(1L, "Andrey", "Vasilyev", "andrey@test.com", 20);

        when(tripRepository.findTripById(1L)).thenReturn(Optional.of(trip));
        when(bookingRepository.existsByEmailAndTripId("andrey@test.com", 1L)).thenReturn(true);

        assertThrows(EmailAlreadyOnTripException.class, () -> bookingService.takeBooking(request));

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void takeBooking_ShouldThrowTripNotFoundException_WhenTripIsMissing() {
        TakeBookingRequest request = new TakeBookingRequest(1L, "Andrey", "Vasilyev", "andrey@test.com", 20);
        when(tripRepository.findTripById(1L)).thenReturn(Optional.empty());

        assertThrows(TripNotFoundException.class, () -> bookingService.takeBooking(request));
    }

    @Test
    void refundBooking_ShouldReturnTrue_WhenRefundCodeExists() throws ExecutionException, InterruptedException {
        RefundBookingRequest request = new RefundBookingRequest("ABC");

        when(bookingRepository.existsByRefundCode("ABC")).thenReturn(true);

        CompletableFuture<RefundBookingResponse> response = bookingService.refundBooking(request);

        assertTrue(response.get().success());
        verify(bookingRepository, times(1)).deleteBookingByRefundCode("ABC");
    }

    @Test
    void refundBooking_ShouldReturnFalse_WhenRefundCodeDoesNotExist() throws ExecutionException, InterruptedException {
        RefundBookingRequest request = new RefundBookingRequest("INVALID_CODE");

        when(bookingRepository.existsByRefundCode("INVALID_CODE")).thenReturn(false);

        CompletableFuture<RefundBookingResponse> response = bookingService.refundBooking(request);

        assertFalse(response.get().success());
        verify(bookingRepository, never()).deleteBookingByRefundCode(any());
    }
}