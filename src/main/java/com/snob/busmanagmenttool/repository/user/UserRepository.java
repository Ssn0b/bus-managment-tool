package com.snob.busmanagmenttool.repository.user;

import com.snob.busmanagmenttool.model.entity.user.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(UUID driverId);
    Optional<User> findByConfirmationToken(UUID confirmationToken);
    boolean existsByEmailOrUsername(String email, String username);
}
