package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.IngredientDto;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.IngredientJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class IngredientServiceImp implements IngredientService {

    private final IngredientJpaRepository ingredientJpaRepository;

    @Override
    public List<IngredientDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, IngredientDto delivererDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<IngredientDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
