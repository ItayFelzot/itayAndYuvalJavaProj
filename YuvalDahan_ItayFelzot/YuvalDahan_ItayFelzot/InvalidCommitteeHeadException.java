package YuvalDahan_ItayFelzot;

public class InvalidCommitteeHeadException extends CollegeException {
    public InvalidCommitteeHeadException() {
        super("Head of committee must be a doctor or professor and cannot belong to another committee.");
    }
}
