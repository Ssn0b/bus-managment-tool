package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.user.BusDriver;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findBusByDriverId(Long driverId);
    boolean existsBusByDriverId(Long id);
    @Modifying
    @Query("UPDATE Bus b SET b.driver = null WHERE b.driver.id = :driverId")
    void updateDriverIdToNull(Long driverId);

}
