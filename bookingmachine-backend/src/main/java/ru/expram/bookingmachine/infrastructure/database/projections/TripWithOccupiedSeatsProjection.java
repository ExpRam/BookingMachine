package ru.expram.bookingmachine.infrastructure.database.projections;

public interface TripWithOccupiedSeatsProjection {
    Long getTripId();
    Integer getOccupiedSeatsCount();
}
