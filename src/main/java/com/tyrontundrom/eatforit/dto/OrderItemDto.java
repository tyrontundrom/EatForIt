package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.MenuItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OrderItemDto {

    @NotNull
    private UUID uuid;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private MenuItem menuItem;
}
