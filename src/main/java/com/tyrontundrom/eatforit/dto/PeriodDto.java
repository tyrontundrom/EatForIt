package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.validator.PeriodConstraint;
import jakarta.persistence.Embeddable;
import lombok.*;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PeriodConstraint
@Embeddable
public class PeriodDto {

    public static class View {
        public interface Basic {}
    }

    @JsonView(View.Basic.class)
    @Nullable
    private LocalDateTime begin;

    @JsonView(View.Basic.class)
    @Nullable
    private LocalDateTime end;
}
