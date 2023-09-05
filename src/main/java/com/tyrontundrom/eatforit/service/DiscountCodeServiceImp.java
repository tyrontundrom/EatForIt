package com.tyrontundrom.eatforit.service;

import com.google.common.base.Objects;
import com.tyrontundrom.eatforit.dto.DiscountCodeDto;
import com.tyrontundrom.eatforit.dto.RestaurantDto;
import com.tyrontundrom.eatforit.dto.UserDto;
import com.tyrontundrom.eatforit.model.DiscountCode;
import com.tyrontundrom.eatforit.model.Restaurant;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.repo.*;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tyrontundrom.eatforit.utils.ConverterUtils.convert;

@Service
@AllArgsConstructor
public class DiscountCodeServiceImp implements DiscountCodeService {

    private final DiscountCodeJpaRepository discountCodeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public List<DiscountCodeDto> getAll() {
        return discountCodeJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DiscountCodeDto discountCodeDto) {
        if (!Objects.equal(discountCodeDto.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<User> users = new ArrayList<>();
        if (discountCodeDto.getUserDtos() != null) {
            for (UserDto userDto : discountCodeDto.getUserDtos()) {
                User user = userJpaRepository.findByUuid(userDto.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
                users.add(user);
            }
        }

        List<Restaurant> restaurants = new ArrayList<>();
        if (discountCodeDto.getRestaurantDtos() != null) {
            for (RestaurantDto restaurantDtos : discountCodeDto.getRestaurantDtos()) {
                Restaurant restaurant = restaurantJpaRepository.findByUuid(restaurantDtos.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
                restaurants.add(restaurant);
            }
        }

        DiscountCode discountCode = discountCodeJpaRepository.findByUuid(discountCodeDto.getUuid())
                .orElseGet(() -> newDiscountCode(uuid));

        discountCode.setCode(discountCodeDto.getCode());
        discountCode.setDiscount(discountCodeDto.getDiscount());
        discountCode.setDiscountUnit(discountCodeDto.getDiscountUnit());
        discountCode.setPeriod(convert(discountCodeDto.getPeriodDto()));
        discountCode.setUsers(users);
        discountCode.setRestaurants(restaurants);

        if (discountCode.getId() == null) {
            discountCodeJpaRepository.save(discountCode);
        }
    }

    @Override
    public void delete(UUID uuid) {
        DiscountCode discountCode = discountCodeJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        discountCodeJpaRepository.delete(discountCode);
    }

    @Override
    public Optional<DiscountCodeDto> getByUuid(UUID uuid) {
        return discountCodeJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }


    private DiscountCode newDiscountCode(UUID uuid) {
        return new DiscountCode().builder()
                .uuid(uuid)
                .build();
    }
}
