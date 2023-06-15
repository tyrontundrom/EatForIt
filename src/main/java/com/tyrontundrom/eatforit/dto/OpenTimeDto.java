package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.DayOfWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public PeriodTimeDto getPeriodTimeDto() {
        return periodTimeDto;
    }

    public void setPeriodTimeDto(PeriodTimeDto periodTimeDto) {
        this.periodTimeDto = periodTimeDto;
    }

    public RestaurantDto getRestaurantDto() {
        return restaurantDto;
    }

    public void setRestaurantDto(RestaurantDto restaurantDto) {
        this.restaurantDto = restaurantDto;
    }
}
