package br.com.wgomes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WomanListTest {
    final String expectedGender = "FEMININO";
    final List<String> allList = List.of("Alice-Feminino", "Bob-Masculino", "Charlie-MASCULINO", "David-MASC", "Eve-feminino");
    final List<String> womanList = List.of("Alice-Feminino", "Eve-feminino");
    final List<String> manList = List.of("Bob-Masculino", "Charlie-MASCULINO", "David-MASC");

    @Test
    @DisplayName("Verificar se a lista possui mulheres")
    void testWomanList() {
        Long expectedCount = 2L;
        Long womanCount = allList.stream()
                .filter(record -> record.split("\\s*-\\s*")[1].trim().equalsIgnoreCase(expectedGender))
                .count();
        assertEquals(expectedCount, womanCount);
    }

    @Test
    @DisplayName("Verificar se a lista de mulheres está correta")
    void testWomanListContent() {
        assertIterableEquals(womanList, allList.stream()
                .filter(record -> record.split("\\s*-\\s*")[1].trim().equalsIgnoreCase(expectedGender))
                .toList());
    }

    @Test
    @DisplayName("Deve apresentar erro por não possuir mulheres na lista")
    void testWomanListError() {
        Long expectedCount = 2L;
        Long womanCount = manList.stream()
                .filter(record -> record.split("\\s*-\\s*")[1].trim().equalsIgnoreCase(expectedGender))
                .count();
        assertNotEquals(expectedCount, womanCount);
        assertThrows(AssertionError.class, () -> {
            if (!expectedCount.equals(womanCount)) throw new AssertionError("Não possui mulheres na lista");
        });
    }
}
