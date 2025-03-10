package ru.expram.bookingmachine.application.repositories;

import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;
import ru.expram.bookingmachine.domain.models.Trip;

import java.util.List;
import java.util.Optional;

public interface ITripRepository {

    List<Trip> getTrips();
    List<Trip> findTripsByBaseParams(BaseSearchRequest request);
    Optional<Trip> findTripById(long id);
}
