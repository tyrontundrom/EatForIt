package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.repo.RestaurantJpaRepository;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.RestaurantService;
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
@RequestMapping(path = "api/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class RestaurantController {

    private final RestaurantService restaurantService;

    interface RestaurantListView extends RestaurantDto.View.Basic {
    }

    interface RestaurantView extends RestaurantDto.View.Extended, LogginDataDto.View.Basic, CompanyDataDto.View.Basic {
    }

    interface DataUpdateValidation extends Default, RestaurantDto.DataUpdateValidation {
    }


    @JsonView(RestaurantListView.class)
    @GetMapping
    public List<RestaurantDto> getAll() {
        return restaurantService.getAll();
    }

    @JsonView(RestaurantView.class)
    @GetMapping("{uuid}")
    public RestaurantDto get(@PathVariable UUID uuid) {
        return restaurantService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(DataUpdateValidation.class)
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid RestaurantDto restaurantDto) {
        restaurantService.put(uuid, restaurantDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        restaurantService.delete(uuid);
    }
}
