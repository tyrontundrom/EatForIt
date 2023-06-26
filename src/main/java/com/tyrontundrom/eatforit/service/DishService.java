package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.DishDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishService {
    List<DishDto> getAll();
    void put(UUID uuid, DishDto dishDto);
    void delete(UUID uuid);
    Optional<DishDto> getByUuid(UUID uuid);
}
