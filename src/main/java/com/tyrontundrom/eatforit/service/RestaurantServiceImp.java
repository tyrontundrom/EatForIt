package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.RestaurantDto;
import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.Restaurant;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.repo.RestaurantJpaRepository;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RestaurantServiceImp implements RestaurantService {

    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public List<RestaurantDto> getAll() {
        return restaurantJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, RestaurantDto restaurantDto) {

    }

    @Override
    public void delete(UUID uuid) {
        Restaurant restaurant = restaurantJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        restaurantJpaRepository.delete(restaurant);
    }

    @Override
    public Optional<RestaurantDto> getByUuid(UUID uuid) {
        return restaurantJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }
}
