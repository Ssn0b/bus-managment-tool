package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsRouteByBusId(Long id);
}
