package br.com.wgomes.unit.domain.valueobject;

import br.com.wgomes.exceptions.InvalidFormatException;
import br.com.wgomes.domain.valueobject.Cpf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfUnitTest {

    @Test
    void ValidCpf() {
        // Arrange
        Long cpf = 12345678909L;

        // Act & Assert
        assertDoesNotThrow(() -> new Cpf(cpf));
    }

    @Test
    void ValidCpfWithMask() {
        // Arrange
        String cpf = "123.456.789-09";

        // Act & Assert
        assertDoesNotThrow(() -> new Cpf(cpf));
    }

    @Test
    void InvalidCpf() {
        // Arrange
        String cpf = "1234567890";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Cpf(cpf));
    }

    @Test
    void InvalidCpfWithLetters() {
        // Arrange
        String cpf = "1234567890a";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Cpf(cpf));
    }

    @Test
    void InvalidCpfLength() {
        // Arrange
        Long cpf = 1234567891234L;

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Cpf(cpf));
    }

    @Test
    void InvalidCpfAllOne() {
        // Arrange
        String cpf = "111.111.111-11";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Cpf(cpf));
    }

    @Test
    void EmptyCpf() {
        // Arrange
        String cpf = "";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Cpf(cpf));
    }

    @Test
    void nullCpf() {
        // Arrange
        String cpf = null;

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Cpf(cpf));
    }
}