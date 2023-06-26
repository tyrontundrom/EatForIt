package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DishDto;
import com.tyrontundrom.eatforit.repo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class DishServiceImp implements DishService {

    private final DishJpaRepository dishJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<DishDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, DishDto dishDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<DishDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
