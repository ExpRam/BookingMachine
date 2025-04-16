package ru.expram.bookingmachine.infrastructure.repositories;

import lombok.RequiredArgsConstructor;
import ru.expram.bookingmachine.application.dtos.get.BaseSearchRequest;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.infrastructure.database.TripDAO;
import ru.expram.bookingmachine.infrastructure.mapper.TripMapper;
import ru.expram.bookingmachine.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TripRepository implements ITripRepository {

    private final TripDAO tripDAO;

    private final TripMapper tripMapper;

    @Override
    public List<Trip> getTrips() {
        return tripDAO.findAll().stream().map(tripMapper::mapToModel).toList();
    }

    @Override
    public List<Trip> findTripsByBaseParams(BaseSearchRequest request) {
        // Set 59:999 for time (for better search)
        final LocalDateTime departureTime = TimeUtils.adjustTime(request.departureTime());
        final LocalDateTime arrivalTime = TimeUtils.adjustTime(request.arrivalTime());

        return tripDAO.findTripsByBaseParams(
                request.departureCity(), request.arrivalCity(),
                departureTime, arrivalTime,
                request.transportType()
        ).stream().map(tripMapper::mapToModel).toList();
    }

    @Override
    public Optional<Trip> findTripById(long id) {
        return tripDAO.findById(id).map(tripMapper::mapToModel);
    }
}
