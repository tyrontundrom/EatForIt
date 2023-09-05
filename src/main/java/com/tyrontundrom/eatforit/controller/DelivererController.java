package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
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
@RequestMapping(path = "api/deliverers", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DelivererController {

    private final DelivererService delivererService;

    interface DelivererListView extends DelivererDto.View.Basic, PersonalDataDto.View.Basic {}
    interface DelivererView extends EmployeeDto.View.Extended, PersonalDataDto.View.Extended,
            LogginDataDto.View.Basic, OrderDto.View.Extended {}

    interface NewDelivererValidation extends Default, DelivererDto.NewDelivererValidation {}

    @JsonView(DelivererListView.class)
    @GetMapping
    public List<DelivererDto> getAll() {
        return delivererService.getAll();
    }

    @GetMapping("{uuid}")
    public DelivererDto get(@PathVariable UUID uuid) {
        return delivererService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @JsonView(DelivererView.class)
    @Transactional
    @Validated(NewDelivererValidation.class)
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DelivererDto delivererDto) {
        delivererService.put(uuid, delivererDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        delivererService.delete(uuid);
    }
}
