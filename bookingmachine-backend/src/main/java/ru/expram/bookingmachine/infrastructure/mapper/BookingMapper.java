package ru.expram.bookingmachine.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TripMapper.class)
public interface BookingMapper {

    @Mapping(source = "entity.firstName", target = "fullName.firstName")
    @Mapping(source = "entity.lastName", target = "fullName.lastName")
    @Mapping(source = "entity.email", target = "email.value")
    Booking mapToModel(BookingEntity entity);

    @Mapping(source = "model.fullName.firstName", target = "firstName")
    @Mapping(source = "model.fullName.lastName", target = "lastName")
    @Mapping(source = "model.email.value", target = "email")
    BookingEntity mapToEntity(Booking model);
}
