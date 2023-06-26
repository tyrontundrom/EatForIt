package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.DeliveryAddressDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAddressService {
    List<DeliveryAddressDto> getAll();
    void put(UUID uuid, DeliveryAddressDto deliveryAddressDto);
    void delete(UUID uuid);
    Optional<DeliveryAddressDto> getByUuid(UUID uuid);
}
