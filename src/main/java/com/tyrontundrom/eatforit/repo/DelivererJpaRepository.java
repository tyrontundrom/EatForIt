package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DelivererJpaRepository extends JpaRepository<Deliverer, Long> {
    Optional<Deliverer> findByUuid(UUID uuid);
}
