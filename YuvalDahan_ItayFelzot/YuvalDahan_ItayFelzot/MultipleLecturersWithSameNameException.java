package YuvalDahan_ItayFelzot;

public class MultipleLecturersWithSameNameException extends CollegeException {
    private String lecturerName;

    public MultipleLecturersWithSameNameException(String lecturerName) {
        super("More than one lecturer has the name " + lecturerName + ". Please enter the lecturer id.");
        this.lecturerName = lecturerName;
    }

    public String getLecturerName() {
        return lecturerName;
    }
}
