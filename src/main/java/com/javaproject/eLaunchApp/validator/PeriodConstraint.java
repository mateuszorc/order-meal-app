package com.javaproject.eLaunchApp.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodConstraintValidator.class)
@Documented
public @interface PeriodConstraint {

    String message() default "com.javaproject.eLaunchApp.validator.PeriodConstraint";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
