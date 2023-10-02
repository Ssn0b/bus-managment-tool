package com.snob.busmanagmenttool.repository.user;

import com.snob.busmanagmenttool.model.entity.user.Repairman;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairmanRepository extends JpaRepository<Repairman, Long> {
  Optional<Repairman> findByUsername(String email);

  Optional<Repairman> findByFirstnameAndLastname(String firstname, String lastname);

  Optional<Repairman> findById(UUID driverId);
}
