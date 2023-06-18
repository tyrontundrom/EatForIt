package com.tyrontundrom.eatforit.dto;

import jakarta.validation.constraints.Min;
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
public class DishDto {

    @NotNull
    private UUID uuid;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    private ProductDto productDto;

    @Nullable
    private List<MenuItemDto> menuItemDtos;

    }
