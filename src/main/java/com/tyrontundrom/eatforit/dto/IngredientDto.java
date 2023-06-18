package com.tyrontundrom.eatforit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    private Boolean isAllergen;
}
