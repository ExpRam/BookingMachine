package ru.expram.bookingmachine.application.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class TripService implements ITripService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final ITripDTOMapper tripDTOMapper;
    private final ITripRepository tripRepository;
    private final IBookingRepository bookingRepository;

    // Protected cuz Async annotation creates proxy to this method
    @Async
    protected CompletableFuture<List<TripExtendedDTO>> getTripsWithSeats(List<Trip> trips) {
        List<Long> tripIds = trips.stream().map(Trip::getId).toList();

        Map<Long, Integer> occupiedSeats = bookingRepository.findAllSeatsByTripIds(tripIds);
        List<TripExtendedDTO> result = trips.stream()
                .map(trip -> {
                    // Calculating available seats from occupiedSeats map
                    final int occupied = occupiedSeats.getOrDefault(trip.getId(), 0);
                    final int available = trip.getMaxSeats() - occupied;

                    return tripDTOMapper.toTripExtendedDTO(trip, available);
                })
                .toList();

        return CompletableFuture.completedFuture(
            result
        );
    }

    @Override
    @Async
    public CompletableFuture<List<TripExtendedDTO>> getAllTrips() {
        // `Supply` cuz completedFuture does not support thenCompose
        return CompletableFuture.supplyAsync(tripRepository::getTrips).thenCompose(this::getTripsWithSeats);
    }

    @Override
    public CompletableFuture<List<TripExtendedDTO>> getTripsByFilter(BaseSearchRequest request) {
        return CompletableFuture.supplyAsync(() -> tripRepository.findTripsByBaseParams(request)).thenCompose(this::getTripsWithSeats);
    }

    @Override
    public CompletableFuture<HashMap<String, HashMap<String, List<TripExtendedDTO>>>> getGroupedTrips(BaseSearchRequest request) {
        // Getting all trips that passed all filters
        return this.getTripsByFilter(request).thenApplyAsync(extendedTrips -> {
            // Then grouping them by departureTime
            final HashMap<String, HashMap<String, List<TripExtendedDTO>>> groupedTrips = new LinkedHashMap<>();

            extendedTrips.forEach(trip -> {
                final LocalDateTime departureTime = trip.departureTime();

                // Collecting date and time then forming grouped map
                final String date = departureTime.format(DATE_FORMAT);
                final String time = departureTime.format(TIME_FORMAT);

                groupedTrips
                        .computeIfAbsent(date, timeHash -> new LinkedHashMap<>())
                        .computeIfAbsent(time, trips -> new ArrayList<>()).add(trip);
            });

            return groupedTrips;
        });
    }

    @Override
    public CompletableFuture<Set<Integer>> getAvailableSeats(Long tripId) {
        // Search trip. If not found then throw exception
        final Optional<Trip> tripOptional = tripRepository.findTripById(tripId);
        final Trip trip = tripOptional.orElseThrow(() -> new TripNotFoundException(tripId));

        return CompletableFuture.completedFuture(
                trip.getAvailableSeats(bookingRepository.findAllOccupiedSeatsByTripId(trip.getId()))
        );
    }

    @Override
    public CompletableFuture<TripDTO> getTripById(Long tripId) {
        final Optional<Trip> tripOptional = tripRepository.findTripById(tripId);
        final Trip trip = tripOptional.orElseThrow(() -> new TripNotFoundException(tripId));

        return CompletableFuture.completedFuture(tripDTOMapper.toTripDTO(trip));
    }
}
