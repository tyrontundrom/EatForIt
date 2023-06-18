package com.tyrontundrom.eatforit.dto;

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

    @Nullable
    private LocalDateTime begin;

    @Nullable
    private LocalDateTime end;
}
