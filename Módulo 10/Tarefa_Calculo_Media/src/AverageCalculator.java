public class AverageCalculator {
    public static float calculateNotesAvg(float[] notes) {
        if (notes.length == 0) throw new IllegalArgumentException("As Notas não foram informadas!");

        float total = 0;
        for (float note : notes) {
            total += note;
        }
        return total / notes.length;
    }
}
