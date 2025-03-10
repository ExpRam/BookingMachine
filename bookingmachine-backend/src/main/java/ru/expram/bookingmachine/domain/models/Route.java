package ru.expram.bookingmachine.domain.models;

import lombok.Builder;
import lombok.Data;
import ru.expram.bookingmachine.domain.enums.TransportType;

@Data
@Builder
public class Route {

    private Long id;
    private String departureCity;
    private String arrivalCity;
    private TransportType transportType;
}
