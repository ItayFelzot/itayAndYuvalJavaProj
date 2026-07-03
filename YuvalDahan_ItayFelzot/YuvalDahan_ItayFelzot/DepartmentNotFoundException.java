package YuvalDahan_ItayFelzot;

public class DepartmentNotFoundException extends CollegeException {
    public DepartmentNotFoundException() {
        super("Department was not found in the college.");
    }
}
