package ru.expram.bookingmachine.application.services.impl;

import lombok.AllArgsConstructor;
import ru.expram.bookingmachine.application.dtos.base.TripDTO;
import ru.expram.bookingmachine.application.dtos.base.TripExtendedDTO;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;
import ru.expram.bookingmachine.application.exceptions.TripNotFoundException;
import ru.expram.bookingmachine.application.mapper.ITripDTOMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.application.services.ITripService;
import ru.expram.bookingmachine.domain.models.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@AllArgsConstructor
public class TripService implements ITripService {

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final ITripDTOMapper tripDTOMapper;
    private final ITripRepository tripRepository;
    private final IBookingRepository bookingRepository;

    private List<TripExtendedDTO> getTripsWithSeats(List<Trip> trips) {
        List<Long> tripIds = trips.stream().map(Trip::getId).toList();

        List<Integer> occupiedSeats = bookingRepository.findAllSeatsByTripIds(tripIds);

        return IntStream.range(0, trips.size())
                .mapToObj(i -> {
                    Trip trip = trips.get(i);
                    return tripDTOMapper.toTripExtendedDTO(
                            trip,
                            (trip.getMaxSeats() - occupiedSeats.get(i))
                    );
                }).toList();
    }

    @Override
    public List<TripExtendedDTO> getAllTrips() {
        return getTripsWithSeats(tripRepository.getTrips());
    }

    @Override
    public List<TripExtendedDTO> getTripsByFilter(BaseSearchRequest request) {
        return getTripsWithSeats(tripRepository.findTripsByBaseParams(request));
    }

    @Override
    public HashMap<String, HashMap<String, List<TripExtendedDTO>>> getGroupedTrips(BaseSearchRequest request) {
        // Getting all trips that passed all filters
        final List<TripExtendedDTO> tripDTOs = this.getTripsByFilter(request);

        // Then grouping them by departureTime
        final HashMap<String, HashMap<String, List<TripExtendedDTO>>> groupedTrips = new LinkedHashMap<>();

        tripDTOs.forEach(trip -> {
            final LocalDateTime departureTime = trip.departureTime();

            final String date = departureTime.format(DATE_FORMAT);
            final String time = departureTime.format(TIME_FORMAT);

            final var timeGroupedTrips
                    = groupedTrips.computeIfAbsent(date, timeHash -> new LinkedHashMap<>());

            timeGroupedTrips.computeIfAbsent(time, trips -> new ArrayList<>()).add(trip);
        });

        return groupedTrips;
    }

    @Override
    public Set<Integer> getAvailableSeats(Long tripId) {
        final Optional<Trip> tripOptional = tripRepository.findTripById(tripId);

        if(tripOptional.isEmpty())
            throw new TripNotFoundException(tripId);

        final Trip trip = tripOptional.get();
        return trip.getAvailableSeats(bookingRepository.findAllOccupiedSeatsByTripId(trip.getId()));
    }

    @Override
    public TripDTO getTripById(Long tripId) {
        final Optional<Trip> tripOptional = tripRepository.findTripById(tripId);

        if(tripOptional.isEmpty())
            throw new TripNotFoundException(tripId);

        final Trip trip = tripOptional.get();
        return tripDTOMapper.toTripDTO(trip);
    }
}
