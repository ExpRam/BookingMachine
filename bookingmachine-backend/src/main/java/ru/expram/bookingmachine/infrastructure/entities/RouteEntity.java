package ru.expram.bookingmachine.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.expram.bookingmachine.domain.enums.TransportType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routes")
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String departureCity;

    @Column(nullable = false)
    private String arrivalCity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransportType transportType;
}
