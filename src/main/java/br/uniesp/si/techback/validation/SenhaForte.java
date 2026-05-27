package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhaForteValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SenhaForte {
    String message() default "A senha deve conter letras minúsculas, maiúsculas, números e caracteres especiais, com tamanho entre 8 e 60 caracteres.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}