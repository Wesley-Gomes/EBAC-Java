package br.com.wgomes;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String expectedGender = "FEMININO";
        System.out.println("Digite o nome e gênero separados por traço(-), separe os registros com vírgula:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] records = input.split(",");
        System.out.println("Pessoas do gênero feminino inseridas:");
        Arrays.stream(records)
                .filter(record -> record.split("\\s*-\\s*")[1].trim().equalsIgnoreCase(expectedGender))
                .forEach((person) -> System.out.println(person.split("\\s*-\\s*")[0].trim()));
    }
}
