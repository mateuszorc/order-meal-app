package com.javaproject.eLaunchApp.validator;

import com.javaproject.eLaunchApp.models.PeriodTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeriodTimeConstraintValidator implements ConstraintValidator<PeriodTimeConstraint, PeriodTime> {
    @Override
    public void initialize(PeriodTimeConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(PeriodTime periodTime, ConstraintValidatorContext context) {
        try {
            return periodTime.getBegin() == null || periodTime.getEnd() == null || periodTime.getBegin().isBefore(periodTime.getEnd());
        } catch (Exception e) {
            return false;
        }
    }
}
