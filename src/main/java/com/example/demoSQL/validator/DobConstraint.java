package com.example.demoSQL.validator;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import jakarta.validation.Valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DobValidator.class  )
public @interface DobConstraint {
    String message() default "Birth date is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min();
}
