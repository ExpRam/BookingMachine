package ru.expram.bookingmachine.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.expram.bookingmachine.application.dtos.base.RouteDTO;
import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;
import ru.expram.bookingmachine.application.exceptions.TripNotFoundException;
import ru.expram.bookingmachine.application.mapper.ITripDTOMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.application.services.impl.TripService;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private ITripDTOMapper tripDTOMapper;

    @Mock
    private ITripRepository tripRepository;

    @Mock
    private IBookingRepository bookingRepository;

    @InjectMocks
    private TripService tripService;

    private Route route1, route2;
    private Trip trip1, trip2;
    private RouteDTO routeDTO1, routeDTO2;
    private TripExtendedDTO tripDTO1, tripDTO2;
    private TripDTO tripDTO3;

    @BeforeEach
    void setUp() {
        this.route1 = Route.builder()
                .id(1L)
                .departureCity("Омск")
                .arrivalCity("Новосибирск")
                .transportType(TransportType.AIRLINE)
                .build();

        this.route2 = Route.builder()
                .id(2L)
                .departureCity("Москва")
                .arrivalCity("Казань")
                .transportType(TransportType.MIX)
                .build();

        this.routeDTO1 = new RouteDTO(route1.getDepartureCity(), route1.getArrivalCity(), route1.getTransportType().toString());
        this.routeDTO2 = new RouteDTO(route2.getDepartureCity(), route2.getArrivalCity(), route2.getTransportType().toString());

        this.trip1 = Trip.builder()
                .id(1L)
                .route(this.route1)
                .departureTime(LocalDateTime.of(2025, 1, 15, 13, 0))
                .arrivalTime(LocalDateTime.of(2025, 1, 15, 17, 0))
                .maxSeats(100)
                .price(10000d)
                .build();

        this.trip2 = Trip.builder()
                .id(2L)
                .route(this.route2)
                .departureTime(LocalDateTime.of(2025, 2, 5, 0, 0))
                .arrivalTime(LocalDateTime.of(2025, 2, 5, 12, 45))
                .maxSeats(50)
                .price(15000d)
                .build();

        this.tripDTO1 = new TripExtendedDTO(trip1.getId(), routeDTO1, trip1.getDepartureTime(), trip1.getArrivalTime(),
                trip1.getMaxSeats(), 90, trip1.getPrice());
        this.tripDTO2 = new TripExtendedDTO(trip2.getId(), routeDTO2, trip2.getDepartureTime(), trip2.getArrivalTime(),
                trip2.getMaxSeats(), 40, trip2.getPrice());

        this.tripDTO3 = new TripDTO(trip1.getId(), routeDTO1, trip1.getDepartureTime(), trip1.getArrivalTime(),
                trip1.getMaxSeats(), trip1.getPrice());
    }

    @Test
    void getAllTripsTest() {
        List<Trip> trips = Arrays.asList(trip1, trip2);
        List<TripExtendedDTO> tripDTOs = Arrays.asList(tripDTO1, tripDTO2);
        when(tripRepository.getTrips()).thenReturn(trips);
        when(bookingRepository.findAllSeatsByTripIds(any())).thenReturn(List.of(10, 10));
        when(tripDTOMapper.toTripExtendedDTO(trip1, 90)).thenReturn(tripDTO1);
        when(tripDTOMapper.toTripExtendedDTO(trip2, 40)).thenReturn(tripDTO2);

        List<TripExtendedDTO> result = tripService.getAllTrips();

        assertEquals(2, result.size());
        assertEquals(tripDTOs, result);
        verify(tripRepository, times(1)).getTrips();
        verify(bookingRepository, times(1)).findAllSeatsByTripIds(any());
        verify(tripDTOMapper, times(2)).toTripExtendedDTO(any(Trip.class), any());
    }

    @Test
    void getTripsByFilterTest() {
        BaseSearchRequest request = mock(BaseSearchRequest.class);
        List<Trip> filteredTrips = Collections.singletonList(trip1);
        when(tripRepository.findTripsByBaseParams(request)).thenReturn(filteredTrips);
        when(bookingRepository.findAllSeatsByTripIds(any())).thenReturn(List.of(10, 10));
        when(tripDTOMapper.toTripExtendedDTO(trip1, 90)).thenReturn(tripDTO1);

        List<TripExtendedDTO> result = tripService.getTripsByFilter(request);

        assertEquals(1, result.size());
        assertEquals(tripDTO1, result.getFirst());
        verify(tripRepository, times(1)).findTripsByBaseParams(request);
        verify(bookingRepository, times(1)).findAllSeatsByTripIds(any());
        verify(tripDTOMapper, times(1)).toTripExtendedDTO(any(Trip.class), any());
    }

    @Test
    void getGroupedTripsTest() {
        BaseSearchRequest request = mock(BaseSearchRequest.class);
        when(tripRepository.findTripsByBaseParams(request)).thenReturn(Arrays.asList(trip1, trip2));
        when(bookingRepository.findAllSeatsByTripIds(any())).thenReturn(List.of(10, 10));
        when(tripDTOMapper.toTripExtendedDTO(trip1, 90)).thenReturn(tripDTO1);
        when(tripDTOMapper.toTripExtendedDTO(trip2, 40)).thenReturn(tripDTO2);

        HashMap<String, HashMap<String, List<TripExtendedDTO>>> groupedTrips = tripService.getGroupedTrips(request);

        assertEquals(2, groupedTrips.size());
        assertTrue(groupedTrips.containsKey("2025-01-15"));
        assertEquals(1, groupedTrips.get("2025-01-15").size());
        assertTrue(groupedTrips.get("2025-01-15").containsKey("13:00"));

        assertTrue(groupedTrips.containsKey("2025-02-05"));
        assertEquals(1, groupedTrips.get("2025-02-05").size());
        assertTrue(groupedTrips.get("2025-02-05").containsKey("00:00"));

        List<TripExtendedDTO> tripsAt1300 = groupedTrips.get("2025-01-15").get("13:00");
        List<TripExtendedDTO> tripsAt0000 = groupedTrips.get("2025-02-05").get("00:00");

        assertEquals(1, tripsAt1300.size());
        assertEquals(tripDTO1, tripsAt1300.getFirst());

        assertEquals(1, tripsAt0000.size());
        assertEquals(tripDTO2, tripsAt0000.getFirst());

        verify(tripRepository, times(1)).findTripsByBaseParams(request);
        verify(bookingRepository, times(1)).findAllSeatsByTripIds(any());
        verify(tripDTOMapper, times(2)).toTripExtendedDTO(any(Trip.class), any());
    }

    @Test
    void getAvailableSeats_ShouldReturnAvailableSeats_WhenTripExists() {
        when(tripRepository.findTripById(1L)).thenReturn(Optional.of(trip1));
        when(bookingRepository.findAllOccupiedSeatsByTripId(1L)).thenReturn(Set.of(1, 2, 3, 4, 5));

        Set<Integer> availableSeats = tripService.getAvailableSeats(1L);

        assertEquals(95, availableSeats.size());
        assertFalse(availableSeats.contains(1));
        assertFalse(availableSeats.contains(2));
        assertFalse(availableSeats.contains(3));
        assertFalse(availableSeats.contains(4));
        assertFalse(availableSeats.contains(5));
    }

    @Test
    void getAvailableSeats_ShouldThrowException_WhenTripNotFound() {
        when(tripRepository.findTripById(2L)).thenReturn(Optional.empty());

        assertThrows(TripNotFoundException.class, () -> tripService.getAvailableSeats(2L));

        verify(bookingRepository, never()).findAllOccupiedSeatsByTripId(any());
    }

    @Test
    void getTripById_ShouldReturnTrip_WhenTripExists() {
        when(tripRepository.findTripById(1L)).thenReturn(Optional.of(trip1));
        when(tripDTOMapper.toTripDTO(trip1)).thenReturn(tripDTO3);

        TripDTO result = tripService.getTripById(1L);

        assertEquals(tripDTO3, result);
        verify(tripRepository, times(1)).findTripById(1L);
    }

    @Test
    void getTripById_ShouldThrowException_WhenTripNotFound() {
        when(tripRepository.findTripById(2L)).thenReturn(Optional.empty());

        assertThrows(TripNotFoundException.class, () -> tripService.getTripById(2L));
    }
}