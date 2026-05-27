package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SenhaForteValidator implements ConstraintValidator<SenhaForte, String> {

    // Regra: Letras minúsculas, maiúsculas, números e caracteres especiais
    // Tamanho: Mínimo 8, Máximo 60 (ajustado conforme solicitado 8 a 60, embora o usuário tenha mencionado 6 posições em uma parte e 8 em outra, seguiremos 8-60 conforme a especificação do PROJETO_20261.md)
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,60}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }
}