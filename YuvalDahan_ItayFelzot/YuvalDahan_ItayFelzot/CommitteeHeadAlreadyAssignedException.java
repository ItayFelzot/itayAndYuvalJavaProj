package YuvalDahan_ItayFelzot;

public class CommitteeHeadAlreadyAssignedException extends CollegeException {
    public CommitteeHeadAlreadyAssignedException() {
        super("This lecturer already belongs to another committee and cannot be a committee head.");
    }
}
