package com.example.demoSQL.validator;


import com.example.demoSQL.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private int min;
    @Override
    public void initialize(DobConstraint constraintAnnotation) {
    this.min = constraintAnnotation.min();
    }
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
       long years = ChronoUnit.YEARS.between(value, LocalDate.now());

        return years >= min;
    }

}
