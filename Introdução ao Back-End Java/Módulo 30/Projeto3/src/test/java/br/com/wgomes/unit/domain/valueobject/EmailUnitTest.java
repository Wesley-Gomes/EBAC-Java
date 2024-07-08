package br.com.wgomes.unit.domain.valueobject;

import br.com.wgomes.domain.valueobject.Email;
import br.com.wgomes.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailUnitTest {

    @Test
    void validEmail() {
        // Arrange
        String email = "test@test.com";

        // Act & Assert
        assertDoesNotThrow(() -> new Email(email));
    }

    @Test
    void invalidEmailNoAtSign() {
        // Arrange
        String email = "testtest.com";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }

    @Test
    void invalidEmailMultipleAtSign() {
        // Arrange
        String email = "test@@test.com";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }

    @Test
    void invalidEmailNoDomain() {
        // Arrange
        String email = "test@.com";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }

    @Test
    void invalidEmailNoTopLevelDomain() {
        // Arrange
        String email = "test@test";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }

    @Test
    void invalidEmailSpecialCharacters() {
        // Arrange
        String email = "test*test@test.com";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }

    @Test
    void emptyEmail() {
        // Arrange
        String email = "";

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }

    @Test
    void nullEmail() {
        // Arrange
        String email = null;

        // Act & Assert
        assertThrows(InvalidFormatException.class, () -> new Email(email));
    }
}