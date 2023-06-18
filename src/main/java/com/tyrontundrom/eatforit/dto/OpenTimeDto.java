package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.DayOfWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OpenTimeDto {

    @NotNull
    private UUID uuid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Embedded
    private PeriodTimeDto periodTimeDto;

    @NotNull
    private RestaurantDto restaurantDto;
}
