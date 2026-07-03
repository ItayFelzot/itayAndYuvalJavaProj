package YuvalDahan_ItayFelzot;

public class LecturerAlreadyInAnotherCommitteeException extends CommitteeMembershipException {
    public LecturerAlreadyInAnotherCommitteeException() {
        super("Lecturer already belongs to another committee.");
    }
}
