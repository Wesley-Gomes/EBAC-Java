import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Person> people = new ArrayList<>();
        List<String> woman = new ArrayList<>();
        List<String> men = new ArrayList<>();

        for (int i = 1; i <= 4; i++){
            System.out.println("Digite o nome e sexo separados por vírgula:");
            String [] input = scanner.next().split(",");
            Person person = new Person();
            person.setName(input[0]);
            person.setGender(input[1]);
            people.add(person);
        }

        people.forEach(p -> {
            switch (p.getGender().toUpperCase()) {
                case "MASCULINO" -> men.add(p.getName());
                case "FEMININO" -> woman.add(p.getName());
            }
        });

        System.out.println("Pessoas do sexo masculino inseridos:");
        men.forEach(System.out::println);

        System.out.println("Pessoas do sexo feminino inseridos:");
        woman.forEach(System.out::println);
    }
}