package ru.expram.bookingmachine.infrastructure.mapper;

import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;

public class RouteMapper implements IModelEntityMapper<Route, RouteEntity> {

    @Override
    public Route mapToModel(RouteEntity entity) {
        return Route.builder()
                .id(entity.getId())
                .departureCity(entity.getDepartureCity())
                .arrivalCity(entity.getArrivalCity())
                .transportType(entity.getTransportType())
                .build();
    }

    @Override
    public RouteEntity mapToEntity(Route model) {
        return RouteEntity.builder()
                .id(model.getId())
                .departureCity(model.getDepartureCity())
                .arrivalCity(model.getArrivalCity())
                .transportType(model.getTransportType())
                .build();
    }
}
