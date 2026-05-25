package br.uniesp.si.techback.utils;

public class CpfValidador {

    public static boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // CPF deve ter 11 dígitos e não pode ser uma sequência de números iguais
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula os dígitos verificadores
        String digitosCalculados = calcularDigitoVerificador(cpf.substring(0, 9));

        // Compara com os dígitos informados no CPF
        return cpf.substring(9, 11).equals(digitosCalculados);
    }

    private static String calcularDigitoVerificador(String strCpf) {
        int soma = 0;
        int peso = 10;

        // Cálculo do 1º dígito verificador
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(strCpf.charAt(i)) * peso;
            peso--;
        }

        int resto = 11 - (soma % 11);
        char digito1 = (resto == 10 || resto == 11) ? '0' : Character.forDigit(resto, 10);

        soma = 0;
        peso = 11;

        // Cálculo do 2º dígito verificador (inclui o 1º dígito calculado)
        String cpfComPrimeiroDigito = strCpf + digito1;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfComPrimeiroDigito.charAt(i)) * peso;
            peso--;
        }

        resto = 11 - (soma % 11);
        char digito2 = (resto == 10 || resto == 11) ? '0' : Character.forDigit(resto, 10);

        return "" + digito1 + digito2;
    }

}
