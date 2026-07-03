package YuvalDahan_ItayFelzot;

public class InvalidIdException extends CollegeException {
    public InvalidIdException() {
        super("Id must contain digits only and cannot be empty.");
    }
}
