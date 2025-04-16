package ru.expram.bookingmachine.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RouteMapper.class)
public interface RouteMapper {

    Route mapToModel(RouteEntity entity);

    RouteEntity mapToEntity(Route model);

}
