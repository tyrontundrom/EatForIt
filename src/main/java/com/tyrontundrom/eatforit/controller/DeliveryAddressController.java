package com.tyrontundrom.eatforit.controller;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.DeliveryAddressDto;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.DeliveryAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/delivery-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @GetMapping
    public List<DeliveryAddressDto> getAll() {
        return null;
    }

    @GetMapping("{uuid}")
    public DeliveryAddressDto get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody DeliveryAddressDto deliveryAddressDto) {

    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {

    }
}
