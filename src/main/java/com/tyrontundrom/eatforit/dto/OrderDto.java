package com.tyrontundrom.eatforit.dto;

import jakarta.persistence.Embedded;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
}
