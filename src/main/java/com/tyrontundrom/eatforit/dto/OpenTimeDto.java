package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class OpenTimeDto {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private PeriodTimeDto periodTimeDto;

    @JsonView(View.Extended.class)
    @NotNull
    private RestaurantDto restaurantDto;
}
