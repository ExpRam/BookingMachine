package ru.expram.bookingmachine.application.services;

import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ITripService {

    CompletableFuture<List<TripExtendedDTO>> getAllTrips();
    CompletableFuture<List<TripExtendedDTO>> getTripsByFilter(BaseSearchRequest request);
    CompletableFuture<HashMap<String, HashMap<String, List<TripExtendedDTO>>>> getGroupedTrips(BaseSearchRequest request);
    CompletableFuture<Set<Integer>> getAvailableSeats(Long tripId);
    CompletableFuture<TripDTO> getTripById(Long tripId);
}
