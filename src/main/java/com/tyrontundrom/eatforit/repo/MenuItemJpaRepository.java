package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByUuid(UUID uuid);
}
