package YuvalDahan_ItayFelzot;

public class DuplicateLecturerException extends CollegeException {
    public DuplicateLecturerException() {
        super("Lecturer already exists in the college.");
    }
}
