package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.Dish;
import com.tyrontundrom.eatforit.model.Ingredient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

class ProductDto {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    private List<IngredientDto> ingredientDtos;

    @Nullable
    private DishDto dishDto;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientDto> getIngredientDtos() {
        return ingredientDtos;
    }

    public void setIngredientDtos(List<IngredientDto> ingredientDtos) {
        this.ingredientDtos = ingredientDtos;
    }

    @Nullable
    public DishDto getDishDto() {
        return dishDto;
    }

    public void setDishDto(@Nullable DishDto dishDto) {
        this.dishDto = dishDto;
    }
}
