package YuvalDahan_ItayFelzot;

public class CommitteeNotFoundException extends CollegeException {
    public CommitteeNotFoundException() {
        super("Committee was not found in the college.");
    }
}
