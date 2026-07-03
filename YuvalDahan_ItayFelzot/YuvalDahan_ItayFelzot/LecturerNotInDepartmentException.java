package YuvalDahan_ItayFelzot;

public class LecturerNotInDepartmentException extends CollegeException {
    public LecturerNotInDepartmentException() {
        super("Lecturer does not belong to this department.");
    }
}
