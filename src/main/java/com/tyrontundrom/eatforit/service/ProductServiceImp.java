package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.ProductDto;
import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.Product;
import com.tyrontundrom.eatforit.repo.*;
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
class ProductServiceImp implements ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final IngredientJpaRepository ingredientJpaRepository;
    private final DishJpaRepository dishJpaRepository;

    @Override
    public List<ProductDto> getAll() {
        return productJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, ProductDto productDto) {

    }

    @Override
    public void delete(UUID uuid) {
        Product product = productJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        productJpaRepository.delete(product);
    }

    @Override
    public Optional<ProductDto> getByUuid(UUID uuid) {
        return productJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }
}
