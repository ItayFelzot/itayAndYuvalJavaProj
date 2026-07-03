package YuvalDahan_ItayFelzot;

public class Doctor extends ArticleWriter {
    public Doctor(String name, String id, Degree degree, String degreeName, float salary) throws CollegeException {
        super(name, id, degree, degreeName, salary);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
