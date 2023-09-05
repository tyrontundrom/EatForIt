package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.DishService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Validated
@Controller
@RequestMapping(path = "api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DishController {

    private final DishService dishService;

    interface DishListView extends DishDto.View.Basic {}
    interface DishView extends DishDto.View.Extended, ProductDto.View.Extended, MenuItemDto.View.Basic {}

    interface DishDataUpdateValidation extends Default, DishDto.DataUpdateValidation {}

    @JsonView(DishListView.class)
    @GetMapping
    public List<DishDto> getAll() {
        return dishService.getAll();
    }

    @JsonView(DishView.class)
    @GetMapping("{uuid}")
    public DishDto get(@PathVariable UUID uuid) {
        return dishService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(DishDataUpdateValidation.class)
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DishDto dishDto) {
        dishService.put(uuid, dishDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        dishService.delete(uuid);
    }
}
