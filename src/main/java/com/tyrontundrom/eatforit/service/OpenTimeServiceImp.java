package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.OpenTimeDto;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OpenTimeJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.repo.RestaurantJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class OpenTimeServiceImp implements OpenTimeService {

    private final OpenTimeJpaRepository openTimeJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public List<OpenTimeDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OpenTimeDto openTimeDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OpenTimeDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
