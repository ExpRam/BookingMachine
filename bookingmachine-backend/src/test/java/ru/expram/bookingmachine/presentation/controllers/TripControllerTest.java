package ru.expram.bookingmachine.presentation.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:sql/data-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTrips_ShouldReturnAllTrips() throws Exception {
        var resultActions = mockMvc.perform(get("/api/trips"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].route.departureCity").value("CITY_A"))
                .andExpect(jsonPath("$[0].route.arrivalCity").value("CITY_B"))
                .andExpect(jsonPath("$[0].route.transportType").value("BUS"))
                .andExpect(jsonPath("$[0].departureTime").isNotEmpty())
                .andExpect(jsonPath("$[0].arrivalTime").isNotEmpty())
                .andExpect(jsonPath("$[0].maxSeats").value(10))
                .andExpect(jsonPath("$[0].price").value(5008.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].route.departureCity").value("CITY_C"))
                .andExpect(jsonPath("$[1].route.arrivalCity").value("CITY_D"))
                .andExpect(jsonPath("$[1].route.transportType").value("AIRLINE"))
                .andExpect(jsonPath("$[1].departureTime").isNotEmpty())
                .andExpect(jsonPath("$[1].arrivalTime").isNotEmpty())
                .andExpect(jsonPath("$[1].maxSeats").value(144))
                .andExpect(jsonPath("$[1].price").value(15915.0));
    }



    @Test
    void getTripsWithFilter_ShouldReturnFilteredTrips() throws Exception {
        var resultActions = mockMvc.perform(
                get("/api/trips/filter")
                        .param("departureCity", "CITY_A")
                        .param("arrivalCity", "CITY_B")
                        .param("transportType", "BUS")
        );

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].route.departureCity").value("CITY_A"))
                .andExpect(jsonPath("$[0].route.arrivalCity").value("CITY_B"))
                .andExpect(jsonPath("$[0].route.transportType").value("BUS"))
                .andExpect(jsonPath("$[0].departureTime").isNotEmpty())
                .andExpect(jsonPath("$[0].arrivalTime").isNotEmpty())
                .andExpect(jsonPath("$[0].maxSeats").value(10))
                .andExpect(jsonPath("$[0].price").value(5008.0));
    }

    @Test
    void getGroupedTrips_ShouldReturnTripsGroupedByDateAndTime() throws Exception {
        var resultActions = mockMvc.perform(
                get("/api/trips/filter/grouped")
                        .param("departureCity", "CITY_A")
                        .param("arrivalCity", "CITY_B")
                        .param("transportType", "BUS")
        );

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.['2025-01-01']").exists())
                .andExpect(jsonPath("$.['2025-01-01'].length()").value(1))
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].id").value(1))
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].route.departureCity").value("CITY_A"))
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].route.arrivalCity").value("CITY_B"))
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].route.transportType").value("BUS"))
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].departureTime").isNotEmpty())
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].arrivalTime").isNotEmpty())
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].maxSeats").value(10))
                .andExpect(jsonPath("$.['2025-01-01']['18:53'][0].price").value(5008.0));
    }

    @Test
    void getSeats_ShouldReturnAvailableSeats() throws Exception {
        mockMvc.perform(get("/api/trips/seats")
                        .param("tripId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(9));
    }

    @Test
    void getSeats_ShouldReturnNotFound_WhenTripDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/trips/seats")
                        .param("tripId", "9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTripById_ShouldReturnTrip_WhenTripExists() throws Exception {
        var resultActions = mockMvc.perform(
                get("/api/trips/search")
                        .param("tripId", "1")
        );

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.route.departureCity").value("CITY_A"))
                .andExpect(jsonPath("$.route.arrivalCity").value("CITY_B"))
                .andExpect(jsonPath("$.route.transportType").value("BUS"))
                .andExpect(jsonPath("$.departureTime").isNotEmpty())
                .andExpect(jsonPath("$.arrivalTime").isNotEmpty())
                .andExpect(jsonPath("$.maxSeats").value(10))
                .andExpect(jsonPath("$.price").value(5008.0));
    }

    @Test
    void getTripById_ShouldReturnTrip_WhenTripDoesNotExists() throws Exception {
        mockMvc.perform(
                get("/api/trips/search")
                        .param("tripId", "5000"))
                .andExpect(status().isNotFound());;
    }
}