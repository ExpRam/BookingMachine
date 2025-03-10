package ru.expram.bookingmachine.infrastructure.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;
import ru.expram.bookingmachine.presentation.TestApplicationConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@Sql(scripts = {"classpath:sql/data-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TripDAOTest {

    @Autowired
    private TripDAO tripDAO;

    @Test
    void findAll_ShouldReturnAllTrips() {
        List<TripEntity> trips = tripDAO.findAll();
        assertEquals(2, trips.size());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnTripsFilteredByDepartureCity() {
        List<TripEntity> trips = tripDAO.findTripsByBaseParams("CITY_A", null, null, null, null);
        assertEquals(1, trips.size());
        assertEquals("CITY_A", trips.get(0).getRoute().getDepartureCity());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnTripsFilteredByArrivalCity() {
        List<TripEntity> trips = tripDAO.findTripsByBaseParams(null, "CITY_B", null, null, null);
        assertEquals(1, trips.size());
        assertEquals("CITY_B", trips.get(0).getRoute().getArrivalCity());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnTripsFilteredByDepartureTime() {
        LocalDateTime departureTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0);
        List<TripEntity> trips = tripDAO.findTripsByBaseParams(null, null, departureTime, null, null);
        assertEquals(2, trips.size());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnTripsFilteredByArrivalTime() {
        LocalDateTime arrivalTime = LocalDateTime.of(2025, 1, 6, 0, 0, 0, 0);
        List<TripEntity> trips = tripDAO.findTripsByBaseParams(null, null, null, arrivalTime, null);
        assertEquals(2, trips.size());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnTripsFilteredByTransportType() {
        List<TripEntity> trips = tripDAO.findTripsByBaseParams(null, null, null, null, TransportType.BUS);
        assertEquals(1, trips.size());
        assertEquals(TransportType.BUS, trips.get(0).getRoute().getTransportType());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnTripWithAllFilters() {
        LocalDateTime departureTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0);
        LocalDateTime arrivalTime = LocalDateTime.of(2025, 1, 6, 0, 0, 0, 0);
        List<TripEntity> trips = tripDAO.findTripsByBaseParams("CITY_A", "CITY_B", departureTime, arrivalTime, TransportType.BUS);
        assertEquals(1, trips.size());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnAllTripsWhenNoFilters() {
        List<TripEntity> trips = tripDAO.findTripsByBaseParams(null, null, null, null, null);
        assertEquals(2, trips.size());
    }

    @Test
    void findTripsByBaseParams_ShouldReturnEmptyListWhenNoTripsMatch() {
        List<TripEntity> trips = tripDAO.findTripsByBaseParams("CITY_X", "CITY_Y", null, null, null);
        assertEquals(0, trips.size());
    }
}