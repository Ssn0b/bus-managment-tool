package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.route.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {}
