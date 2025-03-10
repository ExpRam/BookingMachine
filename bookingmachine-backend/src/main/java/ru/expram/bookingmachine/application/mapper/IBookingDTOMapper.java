package ru.expram.bookingmachine.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.expram.bookingmachine.application.dtos.base.BookingDTO;
import ru.expram.bookingmachine.domain.models.Booking;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IBookingDTOMapper {

    @Mapping(source = "fullName.firstName", target = "firstName")
    @Mapping(source = "fullName.lastName", target = "lastName")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "trip", target = "trip")
    BookingDTO toBookingDTO(Booking booking);
}
