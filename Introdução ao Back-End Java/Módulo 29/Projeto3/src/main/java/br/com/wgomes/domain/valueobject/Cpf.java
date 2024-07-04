package br.com.wgomes.domain.valueobject;

import br.com.wgomes.domain.exception.InvalidFormatException;

public record Cpf(String value) {
    public Cpf {
        if (value == null || !isValid(value)) {
            throw new InvalidFormatException("Invalid CPF");
        }
    }

    public Cpf(Long value) {
        this(value == null ? null : Long.toString(value));
    }

    private static boolean isValid(String cpf) {
        String cpfFormated = cpf.replaceAll("\\D", "");
        if (cpfFormated.length() != 11) {
            return false;
        }
        if (cpfFormated.matches("(\\d)\\1{10}")) {
            return false;
        }
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(cpfFormated.charAt(i));
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);
        firstVerifier = (firstVerifier >= 10) ? 0 : firstVerifier;
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);
        secondVerifier = (secondVerifier >= 10) ? 0 : secondVerifier;
        return (firstVerifier == digits[9]) && (secondVerifier == digits[10]);
    }
}