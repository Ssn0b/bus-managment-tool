package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    boolean existsRouteByBusId(Long id);

}
