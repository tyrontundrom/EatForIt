package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.MenuItemDto;
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
class MenuItemServiceImp implements MenuItemService {

    private final MenuItemJpaRepository menuItemJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DishJpaRepository dishJpaRepository;

    @Override
    public List<MenuItemDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, MenuItemDto menuItemDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<MenuItemDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
