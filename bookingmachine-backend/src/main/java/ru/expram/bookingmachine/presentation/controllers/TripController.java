package ru.expram.bookingmachine.presentation.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;
import ru.expram.bookingmachine.application.services.ITripService;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("api/trips")
public class TripController {

    private final ITripService tripService;

    @GetMapping
    public List<TripExtendedDTO> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/search")
    public TripDTO getTripById(@NotNull Long tripId) {
        return tripService.getTripById(tripId);
    }

    @GetMapping("/filter")
    public List<TripExtendedDTO> getTripsWithFilter(@Valid BaseSearchRequest request) {
        return tripService.getTripsByFilter(request);
    }

    /**
     * @param request the regular BaseSearchRequest
     * @return the map data that contains date, time and the list of TripDTO objects
     */
    @GetMapping("/filter/grouped")
    public HashMap<String, HashMap<String, List<TripExtendedDTO>>> getGroupedTrips(@Valid BaseSearchRequest request) {
        return tripService.getGroupedTrips(request);
    }

    @GetMapping("/seats")
    public Set<Integer> getSeats(Long tripId) {
        return tripService.getAvailableSeats(tripId);
    }
}
