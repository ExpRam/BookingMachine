package ru.expram.bookingmachine.domain.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Builder
public class Trip {

    private Long id;
    private Route route;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Integer maxSeats;
    private Double price;

    public int getRandomAvailableSeat(Set<Integer> availableSeats) {
        final ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        return (int) availableSeats.toArray()[localRandom.nextInt(availableSeats.size())];
    }

    public Set<Integer> getAvailableSeats(Set<Integer> occupiedSeats) {
        return IntStream.range(1, this.maxSeats + 1)
                .filter(seat -> !occupiedSeats.contains(seat))
                .boxed()
                .collect(Collectors.toSet());
    }

}
