package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByUuid(UUID uuid);
}
