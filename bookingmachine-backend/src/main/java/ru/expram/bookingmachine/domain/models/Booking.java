package ru.expram.bookingmachine.domain.models;

import lombok.Builder;
import lombok.Data;
import ru.expram.bookingmachine.domain.valueobjects.Email;
import ru.expram.bookingmachine.domain.valueobjects.FullName;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class Booking {

    private Long id;
    private Trip trip;
    private FullName fullName;
    private Email email;
    private Integer seatNumber;
    private String refundCode;

    public void setRandomOrReadySeat(Set<Integer> availableSeats, Integer seatNumber) {
        if(seatNumber == null) {
            this.seatNumber = trip.getRandomAvailableSeat(availableSeats);
        } else {
            this.seatNumber = seatNumber;
        }
    }

    public void generateRefundCode() {
        this.refundCode = UUID.randomUUID().toString().replaceAll("-", "");
    }
}
