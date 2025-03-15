package ru.expram.bookingmachine.infrastructure.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;
import ru.expram.bookingmachine.utils.SortSettings;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripDAO extends JpaRepository<TripEntity, Long> {

    // Fixing n+1 problem
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "route")
    List<TripEntity> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "route")
    @Query("""
            SELECT t FROM TripEntity t JOIN t.route r WHERE
            (:departureCity IS NULL OR UPPER(r.departureCity) = UPPER(:departureCity)) AND
            (:arrivalCity IS NULL OR UPPER(r.arrivalCity) = UPPER(:arrivalCity) ) AND
            (:departureTime IS NULL OR t.departureTime >= :departureTime) AND
            (:arrivalTime IS NULL OR t.arrivalTime <= :arrivalTime) AND
            (:transportType IS NULL OR r.transportType = :transportType)
        """ + SortSettings.SORT_QUERY)
    List<TripEntity> findTripsByBaseParams(
            String departureCity,
            String arrivalCity,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            TransportType transportType
    );

}
