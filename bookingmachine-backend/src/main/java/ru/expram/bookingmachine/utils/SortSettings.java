package ru.expram.bookingmachine.utils;

public class SortSettings {

    public static final String SORT_QUERY = "ORDER BY (CAST(t.arrivalTime AS INTEGER) - CAST(t.departureTime AS INTEGER)) ASC";
}
