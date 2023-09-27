package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.City;
import com.snob.busmanagmenttool.model.entity.route.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
    Optional<Object> findByCity(City city);
}
