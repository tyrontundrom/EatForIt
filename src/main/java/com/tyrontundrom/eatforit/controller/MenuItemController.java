package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class MenuItemController {

    private final MenuItemService menuItemService;

    interface MenuItemListView extends MenuItemDto.View.Basic, RestaurantDto.View.Basic {}
    interface MenuItemView extends MenuItemDto.View.Extended, RestaurantDto.View.Id, DishDto.View.Id {}

    @JsonView(MenuItemListView.class)
    @GetMapping
    public List<MenuItemDto> getAll() {
        return menuItemService.getAll();
    }

    @JsonView(MenuItemView.class)
    @GetMapping("{uuid}")
    public MenuItemDto get(@PathVariable UUID uuid) {
        return menuItemService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid MenuItemDto menuItemDto) {
        menuItemService.put(uuid, menuItemDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        menuItemService.delete(uuid);
    }
}
