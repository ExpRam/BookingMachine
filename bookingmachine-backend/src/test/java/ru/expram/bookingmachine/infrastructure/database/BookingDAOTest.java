package ru.expram.bookingmachine.infrastructure.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.expram.bookingmachine.presentation.TestApplicationConfiguration;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@Sql(scripts = {"classpath:sql/data-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookingDAOTest {

    @Autowired
    private BookingDAO bookingDAO;

    @Test
    void existsByEmailAndTripId_ShouldReturnTrue_WhenBookingExists() {
        boolean exists = bookingDAO.existsByEmailAndTripId("andrey2@test.com", 1L);
        assertTrue(exists);
    }

    @Test
    void existsByEmailAndTripId_ShouldReturnFalse_WhenBookingDoesNotExist() {
        boolean exists = bookingDAO.existsByEmailAndTripId("unknown@test.com", 1L);
        assertFalse(exists);
    }

    @Test
    void existsByRefundCode_ShouldReturnTrue_WhenRefundCodeExists() {
        boolean exists = bookingDAO.existsByRefundCode("480305cbe5534b888dbebfeda8507df9");
        assertTrue(exists);
    }

    @Test
    void existsByRefundCode_ShouldReturnFalse_WhenRefundCodeDoesNotExist() {
        boolean exists = bookingDAO.existsByRefundCode("INVALID_CODE");
        assertFalse(exists);
    }

    @Test
    void findAllSeatsByTripId_ShouldReturnOccupiedSeats() {
        Set<Integer> occupiedSeats = bookingDAO.findAllSeatsByTripId(1L);
        assertNotNull(occupiedSeats);
        assertEquals(1, occupiedSeats.size());
        assertTrue(occupiedSeats.contains(7));
    }

    @Test
    void findAllSeatsByTripIds_ShouldReturnCountOfOccupiedSeatsForEveryId() {
        List<Integer> occupiedSeats = bookingDAO.findAllSeatsByTripIds(Set.of(1L));
        assertNotNull(occupiedSeats);
        assertEquals(1, occupiedSeats.size());
        assertTrue(occupiedSeats.contains(1));
    }

    @Test
    void deleteByRefundCode_ShouldRemoveBooking() {
        bookingDAO.deleteByRefundCode("480305cbe5534b888dbebfeda8507df9");
        boolean exists = bookingDAO.existsByRefundCode("480305cbe5534b888dbebfeda8507df9");
        assertFalse(exists);
    }
}