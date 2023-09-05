package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DeliveryAddressService;
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
@RequestMapping(path = "api/delivery-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    interface DeliveryAddressListView extends DeliveryAddressDto.View.Basic, UserDto.View.Id {}
    interface DeliveryAddressView extends DeliveryAddressDto.View.Extended, UserDto.View.Id {}

    @JsonView(DeliveryAddressListView.class)
    @GetMapping
    public List<DeliveryAddressDto> getAll() {
        return deliveryAddressService.getAll();
    }

    @JsonView(DeliveryAddressView.class)
    @GetMapping("{uuid}")
    public DeliveryAddressDto get(@PathVariable UUID uuid) {
        return deliveryAddressService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid DeliveryAddressDto deliveryAddressDto) {
        deliveryAddressService.put(uuid, deliveryAddressDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        deliveryAddressService.delete(uuid);
    }
}
