package com.tyrontundrom.eatforit.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodConstraintValidator.class)
@Documented
public @interface PeriodTimeConstraint {
    String message() default "{pl.tyrontundrom.eatforit.validator.PeriodTimeConstraintValidator}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
