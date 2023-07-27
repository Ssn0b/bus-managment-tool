package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {}
