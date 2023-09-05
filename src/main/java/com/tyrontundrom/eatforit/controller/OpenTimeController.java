package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.OpenTimeService;
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
@RequestMapping(path = "api/open-times", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class OpenTimeController {

    private final OpenTimeService openTimeService;

    interface OpenTimeListView extends OpenTimeDto.View.Basic {}
    interface OpenTimeView extends OpenTimeDto.View.Extended, RestaurantDto.View.Id {}

    @JsonView(OpenTimeListView.class)
    @GetMapping
    public List<OpenTimeDto> getAll() {
        return openTimeService.getAll();
    }

    @Transactional
    @PostMapping
    public void post(@RequestBody List<@Valid OpenTimeDto> openTimeDtos) {
        for (OpenTimeDto openTimeDto : openTimeDtos) {
            put(openTimeDto.getUuid(), openTimeDto);
        }
    }

    @JsonView(OpenTimeView.class)
    @GetMapping("{uuid}")
    public OpenTimeDto get(@PathVariable UUID uuid) {
        return openTimeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid OpenTimeDto openTimeDto) {
        openTimeService.put(uuid, openTimeDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        openTimeService.delete(uuid);
    }
}
