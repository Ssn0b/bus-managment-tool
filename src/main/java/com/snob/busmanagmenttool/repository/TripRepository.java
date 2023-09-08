package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsRouteByBusId(UUID id);

    Optional<Trip> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
