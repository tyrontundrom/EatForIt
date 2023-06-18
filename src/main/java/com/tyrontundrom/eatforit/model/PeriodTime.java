package com.tyrontundrom.eatforit.model;

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
public class PeriodTime {

    @Nullable
    private LocalTime begin;

    @Nullable
    private LocalTime end;
}
