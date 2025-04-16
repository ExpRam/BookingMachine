package ru.expram.bookingmachine.infrastructure.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RouteMapperTest {

    private RouteMapper routeMapper = Mappers.getMapper(RouteMapper.class);

    @Test
    void mapToModel_ShouldMapCorrectly() {
        RouteEntity entity = RouteEntity.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        Route model = routeMapper.mapToModel(entity);

        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getDepartureCity(), model.getDepartureCity());
        assertEquals(entity.getArrivalCity(), model.getArrivalCity());
        assertEquals(entity.getTransportType(), model.getTransportType());
    }

    @Test
    void mapToEntity_ShouldMapCorrectly() {
        Route model = Route.builder()
                .id(1L)
                .departureCity("Москва")
                .arrivalCity("Санкт-Петербург")
                .transportType(TransportType.BUS)
                .build();

        RouteEntity entity = routeMapper.mapToEntity(model);

        assertNotNull(entity);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getDepartureCity(), entity.getDepartureCity());
        assertEquals(model.getArrivalCity(), entity.getArrivalCity());
        assertEquals(model.getTransportType(), entity.getTransportType());
    }
}
