package YuvalDahan_ItayFelzot;

public class Committee implements Cloneable {
    private String name;
    private Lecturer[] lecturers = new Lecturer[1];
    private int amountOfLecturers = 0;
    private Lecturer headOfCommittee;

    public Committee(String name, Lecturer headOfCommittee) throws CollegeException {
        setName(name);
        setHeadOfCommittee(headOfCommittee);
    }

    public Committee(Committee committee) {
        setName(committee.getName());
        setLecturers(committee.getLecturers());
        this.headOfCommittee = committee.getHeadOfCommittee();
    }

    public String getName() {
        return name;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public int getAmountOfLecturers() {
        return amountOfLecturers;
    }

    public Lecturer getHeadOfCommittee() {
        return headOfCommittee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLecturers(Lecturer[] lecturers) {
        if (lecturers == null) {
            return;
        }
        this.lecturers = new Lecturer[Math.max(1, lecturers.length)];
        int amount = 0;
        for (int i = 0; i < lecturers.length; i++) {
            if (lecturers[i] == null) {
                continue;
            }
            this.lecturers[i] = lecturers[i];
            amount++;
        }
        this.amountOfLecturers = amount;
    }

    public void setHeadOfCommittee(Lecturer headOfCommittee) throws CollegeException {
        if (headOfCommittee == null) {
            throw new InvalidCommitteeHeadException();
        }
        if (headOfCommittee.getDegree().compareTo(Lecturer.Degree.Doctor) < 0) {
            throw new InvalidCommitteeHeadException();
        }
        if (headOfCommittee.belongsToAnyCommittee() && !isHeadOfCommittee(headOfCommittee)) {
            throw new CommitteeHeadAlreadyAssignedException();
        }
        if (this.headOfCommittee != null) {
            this.headOfCommittee.removeCommitteeFromLecturer(this);
        }
        this.headOfCommittee = headOfCommittee;
        headOfCommittee.addCommitteeToLecturer(this);
    }

    public boolean isLecturerInCommittee(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i] != null && lecturers[i].equals(lecturer)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHeadOfCommittee(Lecturer lecturer) {
        if (lecturer == null || this.headOfCommittee == null) {
            return false;
        }
        return this.headOfCommittee.equals(lecturer);
    }

    public void addLecturerToCommittee(Lecturer lecturer) throws CollegeException {
        if (lecturer == null) {
            throw new LecturerNotFoundException();
        }
        if (isLecturerInCommittee(lecturer) || isHeadOfCommittee(lecturer)) {
            throw new LecturerAlreadyInCommitteeException();
        }
        if (lecturer.belongsToAnyCommittee()) {
            throw new LecturerAlreadyInAnotherCommitteeException();
        }
        Lecturer[] newLecturers;
        if (amountOfLecturers == lecturers.length) {
            newLecturers = new Lecturer[Math.max(1, lecturers.length * 2)];
        } else {
            newLecturers = new Lecturer[lecturers.length];
        }
        for (int i = 0; i < this.amountOfLecturers; i++) {
            newLecturers[i] = lecturers[i];
        }
        newLecturers[this.amountOfLecturers] = lecturer;
        this.lecturers = newLecturers;
        this.amountOfLecturers++;
        lecturer.addCommitteeToLecturer(this);
    }

    public void removeLecturerFromCommittee(Lecturer lecturer) throws CollegeException {
        if (lecturer == null || !isLecturerInCommittee(lecturer)) {
            throw new LecturerNotInCommitteeException();
        }
        Lecturer[] newLecturers = new Lecturer[Math.max(1, this.amountOfLecturers)];
        int j = 0;
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i] != null && !lecturers[i].equals(lecturer)) {
                newLecturers[j] = lecturers[i];
                j++;
            }
        }
        lecturer.removeCommitteeFromLecturer(this);
        this.lecturers = newLecturers;
        this.amountOfLecturers = j;
    }

    public void changeHeadOfCommittee(Lecturer lecturer) throws CollegeException {
        if (lecturer == null) {
            throw new LecturerNotFoundException();
        }
        if (lecturer.getDegree().compareTo(Lecturer.Degree.Doctor) < 0) {
            throw new InvalidCommitteeHeadException();
        }
        if (lecturer.belongsToAnyCommittee() && !isLecturerInCommittee(lecturer) && !isHeadOfCommittee(lecturer)) {
            throw new CommitteeHeadAlreadyAssignedException();
        }
        if (isLecturerInCommittee(lecturer)) {
            removeLecturerFromCommittee(lecturer);
        }
        setHeadOfCommittee(lecturer);
    }

    @Override
    public Committee clone() {
        Committee cloned = new Committee(this);
        cloned.setName("new-" + this.name);
        return cloned;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Committee)) {
            return false;
        }
        Committee otherCommittee = (Committee) other;
        return name != null && name.equals(otherCommittee.getName());
    }

    @Override
    public String toString() {
        String headName;
        if (headOfCommittee == null) {
            headName = "None";
        } else {
            headName = headOfCommittee.getName();
        }
        String info = "Committee name: " + this.name + "\nHead of committee: " + headName + "\nLecturers in committee: ";
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i] != null) {
                info += lecturers[i].getName() + ", ";
            }
        }
        return info;
    }
}

