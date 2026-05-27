package br.uniesp.si.techback.validation;

import br.uniesp.si.techback.enuns.TIPOPESSOA;
import br.uniesp.si.techback.utils.CNPJValidator;
import br.uniesp.si.techback.utils.CpfValidador;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class CnpjCpfValidator implements ConstraintValidator<CnpjCpf, Object> {

    @Override
    public void initialize(CnpjCpf constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Field fieldTipo = value.getClass().getDeclaredField("tipoPessoa");
            Field fieldCpfCnpj = value.getClass().getDeclaredField("cpfCnpj");
            
            fieldTipo.setAccessible(true);
            fieldCpfCnpj.setAccessible(true);

            TIPOPESSOA tipoPessoa = (TIPOPESSOA) fieldTipo.get(value);
            String cpfCnpj = (String) fieldCpfCnpj.get(value);

            if (tipoPessoa == null || cpfCnpj == null) {
                return true;
            }

            boolean isValid = false;
            String message = "";

            if (tipoPessoa == TIPOPESSOA.JURIDICA) {
                isValid = CNPJValidator.isValid(cpfCnpj);
                message = "CNPJ inválido";
            } else if (tipoPessoa == TIPOPESSOA.FISICA) {
                isValid = CpfValidador.validarCPF(cpfCnpj);
                message = "CPF inválido";
            }

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                       .addPropertyNode("cpfCnpj") // Vincula o erro ao campo específico
                       .addConstraintViolation();
            }

            return isValid;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            return true;
        }
    }
}