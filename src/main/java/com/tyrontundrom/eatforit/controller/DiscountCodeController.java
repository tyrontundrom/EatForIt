package com.tyrontundrom.eatforit.controller;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.DiscountCodeDto;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.DiscountCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/discount-codes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DiscountCodeController {

    private final DiscountCodeService discountCodeService;

    @GetMapping
    public List<DiscountCodeDto> getAll() {
        return null;
    }

    @GetMapping("{uuid}")
    public DiscountCodeDto get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody DiscountCodeDto discountCodeDto) {

    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {

    }
}
