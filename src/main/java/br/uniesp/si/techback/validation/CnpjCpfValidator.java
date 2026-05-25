package br.uniesp.si.techback.validation;

import br.uniesp.si.techback.enuns.TIPOPESSOA;
import br.uniesp.si.techback.utils.CNPJValidator;
import br.uniesp.si.techback.utils.CpfValidador;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjCpfValidator implements ConstraintValidator<CnpjCpf, Object> {

    private TIPOPESSOA tipoPessoa;

    @Override
    public void initialize(CnpjCpf cnpjCpf) {
        ConstraintValidator.super.initialize(cnpjCpf);
        this.tipoPessoa = cnpjCpf.tipoPessoa();
    }

    @Override
    public boolean isValid(Object cnpjCpf, ConstraintValidatorContext context) {

        if (tipoPessoa == TIPOPESSOA.JURIDICA) {
           String cnpj = (String) cnpjCpf;
           return CNPJValidator.isValid(cnpj);
        }

        if (tipoPessoa == TIPOPESSOA.FISICA) {
            String cpf = (String) cnpjCpf;
            return CpfValidador.validarCPF(cpf);
        }

        return false;

    }
}