package YuvalDahan_ItayFelzot;

public class CorruptedCollegeFileException extends CollegeException {
    public CorruptedCollegeFileException() {
        super("College data file is corrupted and cannot be loaded.");
    }
}
