package br.uniesp.si.techback.validation;

import br.uniesp.si.techback.enuns.TIPOPESSOA;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CnpjCpfValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CnpjCpf {

    String message() default "CNPJ / CPF inválido";

    TIPOPESSOA tipoPessoa();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}