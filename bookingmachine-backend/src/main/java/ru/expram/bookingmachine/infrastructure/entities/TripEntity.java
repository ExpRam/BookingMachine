package ru.expram.bookingmachine.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trips", indexes = {
        @Index(name = "idx_trip_id", columnList = "id"),
        @Index(name = "idx_trip_arrival_time", columnList = "arrivalTime"),
        @Index(name = "idx_trip_departure_time", columnList = "departureTime"),
        @Index(name = "idx_trip_route", columnList = "route_id")
})
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private Integer maxSeats;

    @Column(nullable = false)
    private Double price;
}
