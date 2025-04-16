package ru.expram.bookingmachine.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.expram.bookingmachine.application.dtos.delete.RefundBookingRequest;
import ru.expram.bookingmachine.application.dtos.post.TakeBookingRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:sql/data-test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void takeBooking_ShouldCreateBooking() throws Exception {
        TakeBookingRequest request = new TakeBookingRequest(2L, "Andrey", "Vasilyev", "andrey@test.com", 2);

        var resultActions = mockMvc.perform(post("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value("Andrey"))
                .andExpect(jsonPath("$.lastName").value("Vasilyev"))
                .andExpect(jsonPath("$.email").value("andrey@test.com"))
                .andExpect(jsonPath("$.trip.id").value(2))
                .andExpect(jsonPath("$.seatNumber").value(2));
    }

    @Test
    void takeBooking_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        TakeBookingRequest request = new TakeBookingRequest(2L, "","Vasilyev", "andrey@test.com", 2);

        mockMvc.perform(post("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refundBooking_ShouldProcessRefund_WhenValidCode() throws Exception {
        RefundBookingRequest request = new RefundBookingRequest("480305cbe5534b888dbebfeda8507df9");

        var resultActions = mockMvc.perform(delete("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(resultActions)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void refundBooking_ShouldReturnFailure_WhenInvalidCode() throws Exception {
        RefundBookingRequest request = new RefundBookingRequest("INVALID_CODE");

        var resultActions = mockMvc.perform(delete("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
