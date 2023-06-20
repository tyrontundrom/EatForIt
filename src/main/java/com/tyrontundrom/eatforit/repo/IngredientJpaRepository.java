package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientJpaRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByUuid(UUID uuid);
}
