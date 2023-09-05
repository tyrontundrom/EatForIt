package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.IngredientService;
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
@RequestMapping(path = "api/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class IngredientController {

    private final IngredientService ingredientService;

    interface IngredientListView extends IngredientDto.View.Basic {}
    interface IngredientView extends IngredientDto.View.Basic {}

    @JsonView(IngredientListView.class)
    @GetMapping
    public List<IngredientDto> getAll() {
        return ingredientService.getAll();
    }

    @JsonView(IngredientView.class)
    @GetMapping("{uuid}")
    public IngredientDto get(@PathVariable UUID uuid) {
        return ingredientService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid IngredientDto ingredientDto) {
        ingredientService.put(uuid, ingredientDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        ingredientService.delete(uuid);
    }
}
