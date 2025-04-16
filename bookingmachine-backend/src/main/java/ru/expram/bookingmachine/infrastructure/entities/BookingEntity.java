package ru.expram.bookingmachine.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookings", indexes = {
        @Index(name = "idx_booking_id", columnList = "id"),
        @Index(name = "idx_booking_trip", columnList = "trip_id"),
        @Index(name = "idx_booking_email_trip", columnList = "email,trip_id"),
        @Index(name = "idx_booking_refund", columnList = "refund_code")
})
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private String refundCode;
}
