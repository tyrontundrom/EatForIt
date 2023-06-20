package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishJpaRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findByUuid(UUID uuid);
}
