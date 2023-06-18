package com.tyrontundrom.eatforit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ProductDto {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    private List<IngredientDto> ingredientDtos;

    @Nullable
    private DishDto dishDto;
}
