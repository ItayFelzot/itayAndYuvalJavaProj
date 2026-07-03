package YuvalDahan_ItayFelzot;

public class InvalidSalaryException extends CollegeException {
    public InvalidSalaryException() {
        super("Salary cannot be negative.");
    }
}
