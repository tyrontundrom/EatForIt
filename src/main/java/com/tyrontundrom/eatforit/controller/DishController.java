package com.tyrontundrom.eatforit.controller;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.DishDto;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DishController {

    private final DishService dishService;

    @GetMapping
    public List<DishDto> getAll() {
        return null;
    }

    @GetMapping("{uuid}")
    public DishDto get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody DishDto dishDto) {

    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {

    }
}
