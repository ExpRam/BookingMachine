package ru.expram.bookingmachine.infrastructure.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripMapperTest {

    @Mock
    private RouteMapper routeMapper;

    @InjectMocks
    private TripMapper tripMapper = Mappers.getMapper(TripMapper.class);

    private RouteEntity routeEntity;
    private Route route;

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
    }

    @Test
    void mapToModel_ShouldMapCorrectly() {
        TripEntity tripEntity = TripEntity.builder()
                .id(1L)
                .route(routeEntity)
                .departureTime(LocalDateTime.of(2025, 2, 19, 8, 30))
                .arrivalTime(LocalDateTime.of(2025, 2, 19, 12, 30))
                .maxSeats(40)
                .price(2000d)
                .build();

        when(routeMapper.mapToModel(routeEntity)).thenReturn(route);

        Trip trip = tripMapper.mapToModel(tripEntity);

        assertNotNull(trip);
        assertEquals(tripEntity.getId(), trip.getId());
        assertEquals(route, trip.getRoute());
        assertEquals(tripEntity.getDepartureTime(), trip.getDepartureTime());
        assertEquals(tripEntity.getArrivalTime(), trip.getArrivalTime());
        assertEquals(tripEntity.getMaxSeats(), trip.getMaxSeats());
        assertEquals(tripEntity.getPrice(), trip.getPrice());

        verify(routeMapper, times(1)).mapToModel(routeEntity);
    }

    @Test
    void mapToEntity_ShouldMapCorrectly() {
        Trip trip = Trip.builder()
                .id(1L)
                .route(route)
                .departureTime(LocalDateTime.of(2025, 2, 19, 8, 30))
                .arrivalTime(LocalDateTime.of(2025, 2, 19, 12, 30))
                .maxSeats(40)
                .price(2000d)
                .build();

        when(routeMapper.mapToEntity(route)).thenReturn(routeEntity);

        TripEntity tripEntity = tripMapper.mapToEntity(trip);

        assertNotNull(tripEntity);
        assertEquals(trip.getId(), tripEntity.getId());
        assertEquals(routeEntity, tripEntity.getRoute());
        assertEquals(trip.getDepartureTime(), tripEntity.getDepartureTime());
        assertEquals(trip.getArrivalTime(), tripEntity.getArrivalTime());
        assertEquals(trip.getMaxSeats(), tripEntity.getMaxSeats());
        assertEquals(trip.getPrice(), tripEntity.getPrice());

        verify(routeMapper, times(1)).mapToEntity(route);
    }
}
