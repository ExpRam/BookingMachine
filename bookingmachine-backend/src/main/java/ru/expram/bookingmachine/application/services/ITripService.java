package ru.expram.bookingmachine.application.services;

import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ITripService {

    List<TripExtendedDTO> getAllTrips();
    List<TripExtendedDTO> getTripsByFilter(BaseSearchRequest request);
    HashMap<String, HashMap<String, List<TripExtendedDTO>>> getGroupedTrips(BaseSearchRequest request);
    Set<Integer> getAvailableSeats(Long tripId);
    TripDTO getTripById(Long tripId);
}
