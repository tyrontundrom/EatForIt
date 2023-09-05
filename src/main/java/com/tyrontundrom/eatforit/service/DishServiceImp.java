package com.tyrontundrom.eatforit.service;

import com.google.common.base.Objects;
import com.tyrontundrom.eatforit.dto.DishDto;
import com.tyrontundrom.eatforit.dto.MenuItemDto;
import com.tyrontundrom.eatforit.model.Dish;
import com.tyrontundrom.eatforit.model.MenuItem;
import com.tyrontundrom.eatforit.model.Product;
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

@Service
@AllArgsConstructor
class DishServiceImp implements DishService {

    private final DishJpaRepository dishJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<DishDto> getAll() {
        return dishJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DishDto dishDto) {
        if (!Objects.equal(dishDto.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Product product = productJpaRepository.findByUuid(dishDto.getProductDto().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<MenuItem> menuItems = new ArrayList<>();
        if (dishDto.getMenuItemDtos() != null) {
            for (MenuItemDto d : dishDto.getMenuItemDtos()) {
                MenuItem menuItem = menuItemJpaRepository.findByUuid(d.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                menuItems.add(menuItem);
            }
        }

        Dish dish = dishJpaRepository.findByUuid(dishDto.getUuid())
                .orElseGet(() -> newDish(uuid));

        dish.setQuantity(dishDto.getQuantity());
        dish.setProduct(product);
        dish.setMenuItems(menuItems);

        if (dish.getId() == null) {
            dishJpaRepository.save(dish);
        }
    }

    @Override
    public void delete(UUID uuid) {
        Dish dish = dishJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dishJpaRepository.delete(dish);
    }

    @Override
    public Optional<DishDto> getByUuid(UUID uuid) {
        return dishJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Dish newDish(UUID uuid) {
        return new Dish().builder()
                .uuid(uuid)
                .build();
    }
}
