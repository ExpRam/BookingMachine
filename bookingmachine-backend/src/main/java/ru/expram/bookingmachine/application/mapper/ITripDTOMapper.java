package ru.expram.bookingmachine.application.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.domain.models.Trip;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ITripDTOMapper {

    TripDTO toTripDTO(Trip trip);

    @Mapping(target = "availableSeatsTotal", expression = "java(availableSeatsTotal)")
    TripExtendedDTO toTripExtendedDTO(Trip trip, @Context Integer availableSeatsTotal);
}
