package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class OrderDto {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal netPrice;

    @JsonView(View.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal grossPrice;

    @JsonView(View.Extended.class)
    @Nullable
    private DiscountCodeDto discountCodedto;

    @JsonView(View.Extended.class)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal amountToPayGross;

    @JsonView(View.Extended.class)
    @Nullable
    @Lob
    private String description;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private OrderStatusDto orderStatusDto;

    @JsonView(View.Extended.class)
    @NotNull
    @Size(min = 1)
    private List<OrderItemDto> orderItemDtos;

    @JsonView(View.Basic.class)
    @NotNull
    private UserDto userDto;

    @JsonView(View.Basic.class)
    @NotNull
    private DelivererDto delivererDto;

    @JsonView(View.Basic.class)
    @NotNull
    private RestaurantDto restaurantDto;
}
