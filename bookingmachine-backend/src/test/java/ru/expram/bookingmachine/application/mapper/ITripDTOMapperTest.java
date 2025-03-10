package ru.expram.bookingmachine.application.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ITripDTOMapperTest {

    private ITripDTOMapper tripDTOMapper;

    @BeforeEach
    void setUp() {
        tripDTOMapper = Mappers.getMapper(ITripDTOMapper.class);
    }

    @Test
    void toTripDTO_ShouldMapCorrectly() {
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

        TripDTO tripDTO = tripDTOMapper.toTripDTO(trip);

        assertNotNull(tripDTO);
        assertEquals(trip.getId(), tripDTO.id());
        assertEquals(trip.getRoute().getDepartureCity(), tripDTO.route().departureCity());
        assertEquals(trip.getRoute().getArrivalCity(), tripDTO.route().arrivalCity());
        assertEquals(trip.getRoute().getTransportType().name(), tripDTO.route().transportType());
        assertEquals(trip.getDepartureTime(), tripDTO.departureTime());
        assertEquals(trip.getArrivalTime(), tripDTO.arrivalTime());
        assertEquals(trip.getMaxSeats(), tripDTO.maxSeats());
        assertEquals(trip.getPrice(), tripDTO.price());
    }

    @Test
    void toTripExtendedDTO_ShouldMapCorrectly() {
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

        TripExtendedDTO tripExtendedDTO = tripDTOMapper.toTripExtendedDTO(trip, 20);

        assertNotNull(tripExtendedDTO);
        assertEquals(trip.getId(), tripExtendedDTO.id());
        assertEquals(trip.getRoute().getDepartureCity(), tripExtendedDTO.route().departureCity());
        assertEquals(trip.getRoute().getArrivalCity(), tripExtendedDTO.route().arrivalCity());
        assertEquals(trip.getRoute().getTransportType().name(), tripExtendedDTO.route().transportType());
        assertEquals(trip.getDepartureTime(), tripExtendedDTO.departureTime());
        assertEquals(trip.getArrivalTime(), tripExtendedDTO.arrivalTime());
        assertEquals(trip.getMaxSeats(), tripExtendedDTO.maxSeats());
        assertEquals(trip.getPrice(), tripExtendedDTO.price());
        assertEquals(20, tripExtendedDTO.availableSeatsTotal());
    }
}
