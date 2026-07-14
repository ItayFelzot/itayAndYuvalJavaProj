package YuvalDahan_ItayFelzot;

public class CommitteeDegreeMismatchException extends CollegeException {
    public CommitteeDegreeMismatchException() {
        super("Lecturer degree does not match this committee member type.");
    }
}
