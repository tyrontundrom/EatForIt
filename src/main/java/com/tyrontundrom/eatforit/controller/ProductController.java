package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class ProductController {

    private final ProductService productService;

    interface ProductListView extends ProductDto.View.Basic {}

    interface ProductView extends ProductDto.View.Extended, IngredientDto.View.Basic, DishDto.View.Basic {}

    @JsonView(ProductListView.class)
    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @JsonView(ProductView.class)
    @GetMapping("{uuid}")
    public ProductDto get(@PathVariable UUID uuid) {
        return productService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid ProductDto productDto) {
        productService.put(uuid, productDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        productService.delete(uuid);
    }
}
