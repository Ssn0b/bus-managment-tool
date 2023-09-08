package com.snob.busmanagmenttool.repository;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findBusByDriverId(UUID driverId);
    boolean existsBusByDriverId(UUID id);
    @Modifying
    @Query("UPDATE Bus b SET b.driver = null WHERE b.driver.id = :driverId")
    void updateDriverIdToNull(UUID driverId);

    Optional<Bus> findById(UUID id);

    void deleteById(UUID id);
}
