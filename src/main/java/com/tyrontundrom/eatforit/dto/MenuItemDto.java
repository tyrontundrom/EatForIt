package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView({View.Basic.class})
    @NotBlank
    private String name;

    @JsonView(View.Extended.class)
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal netPrice;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private VatTax vatTax;

    @JsonView(View.Extended.class)
    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal grossPrice;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(min = 1)
    private List<DishDto> dishDtos;

    @JsonView(View.Extended.class)
    @NotNull
    private RestaurantDto restaurantDto;
}
