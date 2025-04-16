package ru.expram.bookingmachine.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RouteMapper.class)
public interface TripMapper {

    Trip mapToModel(TripEntity entity);

    TripEntity mapToEntity(Trip model);
}
