package YuvalDahan_ItayFelzot;

public class LecturerAlreadyInDepartmentException extends CollegeException {
    public LecturerAlreadyInDepartmentException() {
        super("Lecturer already belongs to this department.");
    }
}
