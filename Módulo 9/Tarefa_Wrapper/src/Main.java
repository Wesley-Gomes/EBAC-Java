import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um valor numérico inteiro: ");
        int value = Integer.parseInt(scanner.next());
        System.out.println((Integer) value);
    }
}