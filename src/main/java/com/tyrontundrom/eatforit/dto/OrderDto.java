package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class OrderDto {

    @NotNull
    private UUID uuid;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal netPrice;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal grossPrice;

    @Nullable
    private DiscountCodeDto discountCodedto;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal amountToPayGross;

    @Nullable
    @Lob
    private String description;

    @NotNull
    @Embedded
    private OrderStatusDto orderStatusDto;

    @NotNull
    @Size(min = 1)
    private List<OrderItemDto> orderItemDtos;

    @NotNull
    private UserDto userDto;

    @NotNull
    private DelivererDto delivererDto;

    @NotNull
    private RestaurantDto restaurantDto;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    @Nullable
    public DiscountCodeDto getDiscountCodedto() {
        return discountCodedto;
    }

    public void setDiscountCodedto(@Nullable DiscountCodeDto discountCodedto) {
        this.discountCodedto = discountCodedto;
    }

    public BigDecimal getAmountToPayGross() {
        return amountToPayGross;
    }

    public void setAmountToPayGross(BigDecimal amountToPayGross) {
        this.amountToPayGross = amountToPayGross;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public OrderStatusDto getOrderStatusDto() {
        return orderStatusDto;
    }

    public void setOrderStatusDto(OrderStatusDto orderStatusDto) {
        this.orderStatusDto = orderStatusDto;
    }

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public DelivererDto getDelivererDto() {
        return delivererDto;
    }

    public void setDelivererDto(DelivererDto delivererDto) {
        this.delivererDto = delivererDto;
    }

    public RestaurantDto getRestaurantDto() {
        return restaurantDto;
    }

    public void setRestaurantDto(RestaurantDto restaurantDto) {
        this.restaurantDto = restaurantDto;
    }
}
