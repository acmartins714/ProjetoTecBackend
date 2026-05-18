package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class GeneroValidationValidator implements ConstraintValidator<GeneroValidation, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if(Objects.nonNull(valor) && valor.equalsIgnoreCase("Terror")) {
            return true;
        }
        return false;
    }

}
