package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.Restaurant;
import com.tyrontundrom.eatforit.model.enums.DiscountUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class DiscountCodeDto {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String code;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal discount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountUnit discountUnit;

    @Nullable
    private List<UserDto> userDtos;

    private List<RestaurantDto> restaurantDtos;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public DiscountUnit getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(DiscountUnit discountUnit) {
        this.discountUnit = discountUnit;
    }

    @Nullable
    public List<UserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(@Nullable List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }

    public List<RestaurantDto> getRestaurantDtos() {
        return restaurantDtos;
    }

    public void setRestaurantDtos(List<RestaurantDto> restaurantDtos) {
        this.restaurantDtos = restaurantDtos;
    }
}
