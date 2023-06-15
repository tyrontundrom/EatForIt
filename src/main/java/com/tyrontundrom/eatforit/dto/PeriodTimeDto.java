package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.validator.PeriodTimeConstraint;
import jakarta.persistence.Embeddable;

import javax.annotation.Nullable;
import java.time.LocalTime;

@PeriodTimeConstraint
@Embeddable
public class PeriodTimeDto {

    @Nullable
    private LocalTime begin;

    @Nullable
    private LocalTime end;

    @Nullable
    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalTime begin) {
        this.begin = begin;
    }

    @Nullable
    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalTime end) {
        this.end = end;
    }
}
