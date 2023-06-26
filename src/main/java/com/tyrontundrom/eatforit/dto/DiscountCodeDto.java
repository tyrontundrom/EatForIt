package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.model.enums.DiscountUnit;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCodeDto {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotBlank
    private String code;

    @JsonView(View.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal discount;

    @JsonView(View.Basic.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountUnit discountUnit;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private PeriodDto periodDto;

    @JsonView(View.Extended.class)
    @Nullable
    private List<UserDto> userDtos;

    @JsonView(View.Extended.class)
    @Nullable
    private List<RestaurantDto> restaurantDtos;
}
