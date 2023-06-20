package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddress, Long> {
    Optional<DeliveryAddress> findByUuid(UUID uuid);
}
