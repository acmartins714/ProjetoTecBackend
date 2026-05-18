package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = GeneroValidationValidator.class)
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneroValidation {

    String message() default "O Genero não esta na lista disponível";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
