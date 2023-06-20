package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUuid(UUID uuid);
}
