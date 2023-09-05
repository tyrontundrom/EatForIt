package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.DiscountCodeService;
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
@RequestMapping(path = "api/discount-codes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DiscountCodeController {

    private final DiscountCodeService discountCodeService;

    interface DiscountCodeListView extends DiscountCodeDto.View.Basic, PeriodDto.View.Basic {}
    interface DiscountCodeView extends DiscountCodeDto.View.Extended, PeriodDto.View.Basic {}

    @JsonView(DiscountCodeListView.class)
    @GetMapping
    public List<DiscountCodeDto> getAll() {
        return discountCodeService.getAll();
    }

    @JsonView(DiscountCodeView.class)
    @GetMapping("{uuid}")
    public DiscountCodeDto get(@PathVariable UUID uuid) {
        return discountCodeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DiscountCodeDto discountCodeDto) {
        discountCodeService.put(uuid, discountCodeDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        discountCodeService.delete(uuid);
    }
}
