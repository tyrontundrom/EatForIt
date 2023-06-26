package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.DiscountCodeDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountCodeService {
    List<DiscountCodeDto> getAll();
    void put(UUID uuid, DiscountCodeDto discountCodeDto);
    void delete(UUID uuid);
    Optional<DiscountCodeDto> getByUuid(UUID uuid);
}
