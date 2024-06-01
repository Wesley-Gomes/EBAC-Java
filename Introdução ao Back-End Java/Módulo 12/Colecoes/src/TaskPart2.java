import java.util.*;

public class TaskPart2 {
    public static void main(String[] args) {
        System.out.println("Digite nome e gênero separados por '-' ('xpto-M','xpto-F'), separe as pessoas por vírgula:");
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.next().split(",");
        Set<Person> people = new TreeSet<>();
        for (String p : input) {
            String[] nameAndGender = p.split("-");
            Person person = new Person();
            person.setName(nameAndGender[0]);
            person.setGender(nameAndGender[1]);
            people.add(person);
        }

        List<String> woman = new ArrayList<>();
        List<String> men = new ArrayList<>();
        people.forEach(p -> {
            switch (p.getGender().toUpperCase()) {
                case "M" -> men.add(p.getName());
                case "F" -> woman.add(p.getName());
            }
        });

        System.out.println("Pessoas do gênero masculino inseridas:");
        men.forEach(System.out::println);

        System.out.println("Pessoas do gênero feminino inseridas:");
        woman.forEach(System.out::println);
    }
}
