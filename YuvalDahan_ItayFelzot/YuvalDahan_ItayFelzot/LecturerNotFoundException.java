package YuvalDahan_ItayFelzot;

public class LecturerNotFoundException extends CollegeException {
    public LecturerNotFoundException() {
        super("Lecturer was not found in the college.");
    }
}
