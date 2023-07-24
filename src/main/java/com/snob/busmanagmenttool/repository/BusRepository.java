package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findBusByDriverId(Long driverId);
    boolean existsBusByDriverId(Long id);

}
