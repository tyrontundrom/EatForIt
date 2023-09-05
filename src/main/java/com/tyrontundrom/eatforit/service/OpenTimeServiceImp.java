package com.tyrontundrom.eatforit.service;

import com.google.common.base.Objects;
import com.tyrontundrom.eatforit.dto.OpenTimeDto;
import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.OpenTime;
import com.tyrontundrom.eatforit.model.Restaurant;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OpenTimeJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.repo.RestaurantJpaRepository;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tyrontundrom.eatforit.utils.ConverterUtils.convert;

@Service
@AllArgsConstructor
class OpenTimeServiceImp implements OpenTimeService {

    private final OpenTimeJpaRepository openTimeJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public List<OpenTimeDto> getAll() {
        return openTimeJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, OpenTimeDto openTimeDto) {
        if (!Objects.equal(openTimeDto.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantJpaRepository.findByUuid(openTimeDto.getRestaurantDto().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        OpenTime openTime = openTimeJpaRepository.findByUuid(openTimeDto.getUuid())
                .orElseGet(() -> newOpenTime(uuid, restaurant));

        if (!Objects.equal(openTime.getRestaurant().getUuid(), openTimeDto.getRestaurantDto().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        openTime.setDayOfWeek(openTimeDto.getDayOfWeek());
        openTime.setPeriodTime(convert(openTimeDto.getPeriodTimeDto()));

        if (openTime.getId() == null) {
            openTimeJpaRepository.save(openTime);
        }
    }

    @Override
    public void delete(UUID uuid) {
        OpenTime openTime = openTimeJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        openTimeJpaRepository.delete(openTime);
    }

    @Override
    public Optional<OpenTimeDto> getByUuid(UUID uuid) {
        return openTimeJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private OpenTime newOpenTime(UUID uuid, Restaurant restaurant) {
        return new OpenTime().builder()
                .uuid(uuid)
                .restaurant(restaurant)
                .build();
    }
}
