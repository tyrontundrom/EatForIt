package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByUuid(UUID uuid);
}
