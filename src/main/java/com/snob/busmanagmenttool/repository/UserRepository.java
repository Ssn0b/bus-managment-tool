package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String email);
}
