package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.VatTax;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal netPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VatTax vatTax;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal grossPrice;

    @NotNull
    @Size(min = 1)
    private List<DishDto> dishDtos;

    @NotNull
    private RestaurantDto restaurantDto;
}
