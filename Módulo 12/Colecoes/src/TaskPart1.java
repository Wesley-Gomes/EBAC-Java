import java.util.*;

public class TaskPart1 {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite os nomes separados por vírgula:");
        Set<String> sortedNames = new TreeSet<>(Arrays.asList(scanner.next().split(",")));
        sortedNames.forEach(System.out::println);
    }
}
