package com.tyrontundrom.eatforit.dto;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class OrderStatusDto {

    @NotNull
    private Instant orderTime;

    @NotNull
    private Boolean isPaid;

    @NotNull
    private Instant giveOutTime;

    @NotNull
    private Instant deliveryTime;
}
