package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.MenuItemDto;
import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.MenuItem;
import com.tyrontundrom.eatforit.repo.*;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class MenuItemServiceImp implements MenuItemService {

    private final MenuItemJpaRepository menuItemJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DishJpaRepository dishJpaRepository;

    @Override
    public List<MenuItemDto> getAll() {
        return menuItemJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, MenuItemDto menuItemDto) {

    }

    @Override
    public void delete(UUID uuid) {
        MenuItem menuItem = menuItemJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        menuItemJpaRepository.delete(menuItem);
    }

    @Override
    public Optional<MenuItemDto> getByUuid(UUID uuid) {
        return menuItemJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }
}
