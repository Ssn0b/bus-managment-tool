package com.snob.busmanagmenttool.repository.user;

import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface BusDriverRepository extends JpaRepository<BusDriver, Long> {
    Optional<BusDriver> findByUsername(String email);

    Optional<BusDriver> findById(UUID driverId);

    void deleteById(UUID driverId);
}
