package ru.expram.bookingmachine.presentation.configurations;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import ru.expram.bookingmachine.domain.enums.TransportType;
import ru.expram.bookingmachine.infrastructure.database.RouteDAO;
import ru.expram.bookingmachine.infrastructure.database.TripDAO;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    private final Faker FAKER = new Faker(Locale.of("ru"));

    private final int COUNT_OF_SEEDED_ROUTES = 10000;
    private final int COUNT_OF_SEEDED_TRIPS = 20000;

    private final RouteDAO routeDAO;
    private final TripDAO tripDAO;


    public DatabaseSeeder(RouteDAO routeDAO, TripDAO tripDAO) {
        this.routeDAO = routeDAO;
        this.tripDAO = tripDAO;
    }

    @Override
    public void run(String... args) {
        if(args.length == 0 || !args[0].equals("--seed")) return;

        ThreadLocalRandom localRandom = ThreadLocalRandom.current();

        for (int i = 0; i < COUNT_OF_SEEDED_ROUTES; i++) {
            String departureCity = FAKER.address().city();
            String arrivalCity = FAKER.address().city();

            while(departureCity.equals(arrivalCity)) {
                arrivalCity = FAKER.address().city();
            }

            RouteEntity route = new RouteEntity();
            route.setDepartureCity(departureCity);
            route.setArrivalCity(arrivalCity);

            route.setTransportType(TransportType.values()[localRandom.nextInt(TransportType.values().length)]);
            routeDAO.save(route);
        }

        List<RouteEntity> routeEntities = routeDAO.findAll();

        for (int i = 0; i < COUNT_OF_SEEDED_TRIPS; i++) {
            TripEntity trip = new TripEntity();
            trip.setRoute(routeEntities.get(localRandom.nextInt(routeEntities.size())));
            trip.setMaxSeats(localRandom.nextInt(50, 200));
            trip.setPrice((double) localRandom.nextInt(1000, 20000));

            LocalDateTime currentDate = LocalDateTime.now()
                    .withMonth(1)
                    .withDayOfMonth(1)
                    .withMinute(0);
            currentDate = currentDate.plusHours(localRandom.nextInt(10, 40));
            trip.setDepartureTime(currentDate);
            trip.setArrivalTime(currentDate.plusHours(localRandom.nextInt(5, 100)));

            tripDAO.save(trip);
        }
    }
}
