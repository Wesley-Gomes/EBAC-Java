public class NoteValidator {

    public String checkApproval(float note) {
        if (approved(note)) return "Aprovado";
        if (recovery(note)) return "Recuperação";
        return "Reprovado";
    }

    private Boolean approved(float note) {
        return note >= 7;
    }

    private Boolean recovery(float note) {
        return note >= 5 && note < 7;
    }
}
