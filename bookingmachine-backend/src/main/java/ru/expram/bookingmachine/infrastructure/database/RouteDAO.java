package ru.expram.bookingmachine.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.expram.bookingmachine.infrastructure.entities.RouteEntity;

@Repository
public interface RouteDAO extends JpaRepository<RouteEntity, Long> { }
