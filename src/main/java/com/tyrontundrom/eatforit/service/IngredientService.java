package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.IngredientDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientService {
    List<IngredientDto> getAll();
    void put(UUID uuid, IngredientDto delivererDto);
    void delete(UUID uuid);
    Optional<IngredientDto> getByUuid(UUID uuid);
}
