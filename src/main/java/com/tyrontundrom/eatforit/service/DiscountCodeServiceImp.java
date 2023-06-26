package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DiscountCodeDto;
import com.tyrontundrom.eatforit.repo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class DiscountCodeServiceImp implements DiscountCodeService {

    private final DiscountCodeJpaRepository discountCodeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public List<DiscountCodeDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, DiscountCodeDto discountCodeDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<DiscountCodeDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
