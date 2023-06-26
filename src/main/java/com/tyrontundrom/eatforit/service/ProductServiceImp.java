package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.ProductDto;
import com.tyrontundrom.eatforit.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class ProductServiceImp implements ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final IngredientJpaRepository ingredientJpaRepository;
    private final DishJpaRepository dishJpaRepository;

    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, ProductDto productDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<ProductDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
