package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountCodeJpaRepository extends JpaRepository<DiscountCode, Long> {
    Optional<DiscountCode> findByUuid(UUID uuid);
}
