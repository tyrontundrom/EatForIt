package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.validator.PeriodTimeConstraint;
import jakarta.persistence.Embeddable;
import lombok.*;

import javax.annotation.Nullable;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PeriodTimeConstraint
@Embeddable
public class PeriodTimeDto {

    public static class View {
        public interface Basic {}
    }

    @JsonView(View.Basic.class)
    @Nullable
    private LocalTime begin;

    @JsonView(View.Basic.class)
    @Nullable
    private LocalTime end;
}
