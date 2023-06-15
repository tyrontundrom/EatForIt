package com.tyrontundrom.eatforit.validator;

import com.tyrontundrom.eatforit.model.Period;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class PeriodConstraintValidator implements ConstraintValidator<PeriodConstraint, Period> {
    @Override
    public void initialize(PeriodConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Period period, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return period.getBegin() == null || period.getEnd() == null || period.getBegin().isBefore(period.getEnd());
        } catch (Exception e) {
            return false;
        }
    }
}
