package YuvalDahan_ItayFelzot;

public class DuplicateDepartmentException extends CollegeException {
    public DuplicateDepartmentException() {
        super("Department already exists in the college.");
    }
}
