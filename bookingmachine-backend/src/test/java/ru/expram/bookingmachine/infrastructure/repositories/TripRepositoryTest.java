package ru.expram.bookingmachine.infrastructure.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.infrastructure.database.TripDAO;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripRepositoryTest {

    @Mock
    private TripDAO tripDAO;

    @Mock
    private IModelEntityMapper<Trip, TripEntity> tripMapper;

    @InjectMocks
    private TripRepository tripRepository;

    private Trip trip;
    private TripEntity tripEntity;
    private Route route;
    private RouteEntity routeEntity;

    @BeforeEach
    void setUp() {
        this.routeEntity = RouteEntity.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        this.route = Route.builder()
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
    void getTrips_ShouldReturnAllTrips() {
        when(tripDAO.findAll()).thenReturn(Collections.singletonList(tripEntity));
        when(tripMapper.mapToModel(tripEntity)).thenReturn(trip);

        List<Trip> result = tripRepository.getTrips();

        assertEquals(1, result.size());
        assertEquals(trip, result.getFirst());
        verify(tripDAO, times(1)).findAll();
        verify(tripMapper, times(1)).mapToModel(tripEntity);
    }

    @Test
    void findTripsByBaseParams_ShouldReturnFilteredTrips() {
        BaseSearchRequest request = new BaseSearchRequest("Москва", "Санкт-Петербург", null, null, TransportType.BUS);

        when(tripDAO.findTripsByBaseParams(
                request.departureCity(),
                request.arrivalCity(),
                request.departureTime(),
                request.arrivalTime(),
                request.transportType()
        )).thenReturn(List.of(tripEntity));
        when(tripMapper.mapToModel(tripEntity)).thenReturn(trip);

        List<Trip> result = tripRepository.findTripsByBaseParams(request);

        assertEquals(1, result.size());
        assertEquals(trip, result.getFirst());
        verify(tripDAO, times(1)).findTripsByBaseParams(request.departureCity(),
                request.arrivalCity(),
                request.departureTime(),
                request.arrivalTime(),
                request.transportType());
        verify(tripMapper, times(1)).mapToModel(tripEntity);
    }

    @Test
    void findTripById_ShouldReturnTrip_WhenFound() {
        when(tripDAO.findById(1L)).thenReturn(Optional.of(tripEntity));
        when(tripMapper.mapToModel(tripEntity)).thenReturn(trip);

        Optional<Trip> result = tripRepository.findTripById(1L);

        assertTrue(result.isPresent());
        assertEquals(trip, result.get());
        verify(tripDAO, times(1)).findById(1L);
        verify(tripMapper, times(1)).mapToModel(tripEntity);
    }

    @Test
    void findTripById_ShouldReturnEmpty_WhenNotFound() {
        when(tripDAO.findById(1L)).thenReturn(Optional.empty());

        Optional<Trip> result = tripRepository.findTripById(1L);

        assertTrue(result.isEmpty());
        verify(tripDAO, times(1)).findById(1L);
        verifyNoInteractions(tripMapper);
    }
}
