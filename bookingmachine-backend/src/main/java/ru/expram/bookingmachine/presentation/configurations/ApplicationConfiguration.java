package ru.expram.bookingmachine.presentation.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.expram.bookingmachine.application.common.IModelEntityMapper;
import ru.expram.bookingmachine.application.mapper.IBookingDTOMapper;
import ru.expram.bookingmachine.application.mapper.ITripDTOMapper;
import ru.expram.bookingmachine.application.repositories.IBookingRepository;
import ru.expram.bookingmachine.application.repositories.ITripRepository;
import ru.expram.bookingmachine.application.services.IBookingService;
import ru.expram.bookingmachine.application.services.ITripService;
import ru.expram.bookingmachine.application.services.impl.BookingService;
import ru.expram.bookingmachine.application.services.impl.TripService;
import ru.expram.bookingmachine.domain.models.Booking;
import ru.expram.bookingmachine.domain.models.Route;
import ru.expram.bookingmachine.domain.models.Trip;
import ru.expram.bookingmachine.infrastructure.database.BookingDAO;
import ru.expram.bookingmachine.infrastructure.database.TripDAO;
import ru.expram.bookingmachine.infrastructure.entities.BookingEntity;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;
import ru.expram.bookingmachine.infrastructure.entities.TripEntity;
import ru.expram.bookingmachine.infrastructure.mapper.BookingMapper;
import ru.expram.bookingmachine.infrastructure.mapper.RouteMapper;
import ru.expram.bookingmachine.infrastructure.mapper.TripMapper;
import ru.expram.bookingmachine.infrastructure.repositories.BookingRepository;
import ru.expram.bookingmachine.infrastructure.repositories.TripRepository;

@Configuration
@EnableJpaRepositories("ru.expram.bookingmachine.infrastructure.database")
@EntityScan("ru.expram.bookingmachine.infrastructure.entities")
@ComponentScan(basePackages = "ru.expram.bookingmachine")
public class ApplicationConfiguration {

    @Bean
    public IModelEntityMapper<Route, RouteEntity> routeMapper() {
        return new RouteMapper();
    }

    @Bean
    public IModelEntityMapper<Trip, TripEntity> modelMapper(
            IModelEntityMapper<Route, RouteEntity> routeMapper) {
        return new TripMapper(routeMapper);
    }

    @Bean
    public IModelEntityMapper<Booking, BookingEntity> bookingMapper(
            IModelEntityMapper<Trip, TripEntity> tripMapper) {
        return new BookingMapper(tripMapper);
    }

    @Bean
    public ITripRepository tripRepository(TripDAO tripDAO,
                                          IModelEntityMapper<Trip, TripEntity> tripMapper) {
        return new TripRepository(tripDAO, tripMapper);
    }

    @Bean
    public IBookingRepository bookingRepository(BookingDAO bookingDAO,
                                                IModelEntityMapper<Booking, BookingEntity> bookingMapper) {
        return new BookingRepository(bookingDAO, bookingMapper);
    }

    @Bean
    public IBookingService bookingService(IBookingDTOMapper IBookingDTOMapper, IBookingRepository bookingRepository, ITripRepository tripRepository) {
        return new BookingService(bookingRepository, tripRepository, IBookingDTOMapper);
    }

    @Bean
    public ITripService tripService(ITripDTOMapper ITripDTOMapper, ITripRepository repository, IBookingRepository bookingRepository) {
        return new TripService(ITripDTOMapper, repository, bookingRepository);
    }
}
