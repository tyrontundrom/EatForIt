package com.tyrontundrom.eatforit.controller;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OpenTimeDto;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.OpenTimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/open-times", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class OpenTimeController {

    private final OpenTimeService openTimeService;

    @GetMapping
    public List<OpenTimeDto> getAll() {
        return null;
    }

    @GetMapping("{uuid}")
    public OpenTimeDto get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody OpenTimeDto openTimeDto) {

    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {

    }
}
