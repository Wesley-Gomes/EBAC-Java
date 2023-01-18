import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        float[] notes = new float[4];
        NoteValidator noteValidator = new NoteValidator();

        System.out.println("Digite as 4 Notas:");
        for (int i = 0; i < 4; i++) {
            notes[i] = scan.nextFloat();
        }

        try {
            System.out.println(noteValidator.checkApproval(AverageCalculator.calculateNotesAvg(notes)));
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}