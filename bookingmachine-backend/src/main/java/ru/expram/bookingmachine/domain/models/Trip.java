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
        return availableSeats.stream()
                .skip(ThreadLocalRandom.current().nextInt(availableSeats.size()))
                .findFirst().get();
    }

    public Set<Integer> getAvailableSeats(Set<Integer> occupiedSeats) {
        return IntStream.rangeClosed(1, maxSeats)
                .filter(seat -> !occupiedSeats.contains(seat))
                .boxed()
                .collect(Collectors.toUnmodifiableSet());
    }

}
