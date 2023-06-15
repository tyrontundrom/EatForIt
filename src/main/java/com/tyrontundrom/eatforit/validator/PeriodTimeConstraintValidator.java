package com.tyrontundrom.eatforit.validator;

import com.tyrontundrom.eatforit.model.Period;
import com.tyrontundrom.eatforit.model.PeriodTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class PeriodTimeConstraintValidator implements ConstraintValidator<PeriodTimeConstraint, PeriodTime> {
    @Override
    public void initialize(PeriodTimeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PeriodTime periodTime, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return periodTime.getBegin() == null || periodTime.getEnd() == null || periodTime.getBegin().isBefore(periodTime.getEnd());
        } catch (Exception e) {
            return false;
        }
    }
}
