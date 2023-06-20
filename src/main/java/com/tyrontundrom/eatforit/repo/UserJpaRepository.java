package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuid(UUID uuid);
}
