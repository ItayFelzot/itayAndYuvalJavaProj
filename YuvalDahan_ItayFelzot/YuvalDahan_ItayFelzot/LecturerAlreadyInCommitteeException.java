package YuvalDahan_ItayFelzot;

public class LecturerAlreadyInCommitteeException extends CommitteeMembershipException {
    public LecturerAlreadyInCommitteeException() {
        super("Lecturer already belongs to this committee.");
    }
}
