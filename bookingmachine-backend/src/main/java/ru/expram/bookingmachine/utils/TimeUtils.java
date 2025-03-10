package ru.expram.bookingmachine.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TimeUtils {

    public LocalDateTime adjustTime(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.withSecond(59).withNano(LocalDateTime.MAX.getNano());
    }
}
