package com.snob.busmanagmenttool.repository.user;

import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BusDriverRepository extends JpaRepository<BusDriver, Long> {
    Optional<BusDriver> findByUsername(String email);
}
