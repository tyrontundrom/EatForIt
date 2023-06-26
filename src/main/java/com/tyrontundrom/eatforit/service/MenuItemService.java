package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.MenuItemDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemService {
    List<MenuItemDto> getAll();
    void put(UUID uuid, MenuItemDto menuItemDto);
    void delete(UUID uuid);
    Optional<MenuItemDto> getByUuid(UUID uuid);
}
