package com.tyrontundrom.eatforit.model;

import com.tyrontundrom.eatforit.validator.PeriodConstraint;
import jakarta.persistence.Embeddable;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@PeriodConstraint
@Embeddable
public class Period {

    @Nullable
    private LocalDateTime begin;

    @Nullable
    private LocalDateTime end;

    @Nullable
    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalDateTime begin) {
        this.begin = begin;
    }

    @Nullable
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalDateTime end) {
        this.end = end;
    }
}
