package ru.expram.bookingmachine.infrastructure.mapper;

import lombok.AllArgsConstructor;
import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

@AllArgsConstructor
public class TripMapper implements IModelEntityMapper<Trip, TripEntity> {

    private final IModelEntityMapper<Route, RouteEntity> routeMapper;

    @Override
    public Trip mapToModel(TripEntity entity) {
        return Trip.builder()
                .id(entity.getId())
                .route(routeMapper.mapToModel(entity.getRoute()))
                .departureTime(entity.getDepartureTime())
                .arrivalTime(entity.getArrivalTime())
                .maxSeats(entity.getMaxSeats())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public TripEntity mapToEntity(Trip model) {
        return TripEntity.builder()
                .id(model.getId())
                .route(routeMapper.mapToEntity(model.getRoute()))
                .departureTime(model.getDepartureTime())
                .arrivalTime(model.getArrivalTime())
                .maxSeats(model.getMaxSeats())
                .price(model.getPrice())
                .build();
    }
}
