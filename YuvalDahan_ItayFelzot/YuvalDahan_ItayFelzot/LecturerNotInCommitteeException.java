package YuvalDahan_ItayFelzot;

public class LecturerNotInCommitteeException extends CommitteeMembershipException {
    public LecturerNotInCommitteeException() {
        super("Lecturer does not belong to this committee.");
    }
}
