package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.RestaurantDto;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.repo.RestaurantJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class RestaurantServiceImp implements RestaurantService {

    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public List<RestaurantDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, RestaurantDto restaurantDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<RestaurantDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
