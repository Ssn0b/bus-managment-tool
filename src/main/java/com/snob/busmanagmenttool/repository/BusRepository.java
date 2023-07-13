package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.Bus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query(value = "SELECT u FROM Bus u")
    List<Bus> findAllBuses();

}
