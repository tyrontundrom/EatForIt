package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class OrderStatusDto {

    public static class View {
        public interface Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private Instant orderTime;

    @JsonView(View.Basic.class)
    @NotNull
    private Boolean isPaid;

    @JsonView(View.Basic.class)
    @NotNull
    private Instant giveOutTime;

    @JsonView(View.Basic.class)
    @NotNull
    private Instant deliveryTime;
}
