package YuvalDahan_ItayFelzot;

import java.io.Serializable;
import java.util.ArrayList;

public class Committee<T extends Lecturer> implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    public enum MemberType {FirstDegree, SecondDegree, Doctor, Professor}

    private String name;
    private ArrayList<T> lecturers = new ArrayList<T>();
    private Lecturer headOfCommittee;
    private MemberType memberType;

    public Committee(String name, Lecturer headOfCommittee, MemberType memberType) throws CollegeException {
        setName(name);
        setMemberType(memberType);
        setHeadOfCommittee(headOfCommittee);
    }

    public Committee(Committee<T> committee) {
        setName(committee.getName());
        setMemberType(committee.getMemberType());
        setLecturers(committee.getLecturers());
        this.headOfCommittee = committee.getHeadOfCommittee();
    }

    public String getName() {
        return name;
    }

    public ArrayList<T> getLecturers() {
        return lecturers;
    }

    public int getAmountOfLecturers() {
        return lecturers.size();
    }

    public Lecturer getHeadOfCommittee() {
        return headOfCommittee;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemberType(MemberType memberType) {
        if (memberType == null) {
            this.memberType = MemberType.FirstDegree;
        } else {
            this.memberType = memberType;
        }
    }

    public void setLecturers(ArrayList<T> lecturers) {
        this.lecturers = new ArrayList<T>();
        if (lecturers == null) {
            return;
        }
        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i) != null) {
                this.lecturers.add(lecturers.get(i));
            }
        }
    }

    public void setHeadOfCommittee(Lecturer headOfCommittee) throws CollegeException {
        if (headOfCommittee == null) {
            throw new InvalidCommitteeHeadException();
        }
        if (headOfCommittee.getDegree().compareTo(Lecturer.Degree.Doctor) < 0) {
            throw new InvalidCommitteeHeadException();
        }
        if (headOfCommittee.belongsToAnyCommittee() && isHeadOfCommittee(headOfCommittee) == false) {
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
        return lecturers.contains(lecturer);
    }

    public boolean isHeadOfCommittee(Lecturer lecturer) {
        if (lecturer == null || headOfCommittee == null) {
            return false;
        }
        return headOfCommittee.equals(lecturer);
    }

    @SuppressWarnings("unchecked")
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
        if (matchesMemberType(lecturer) == false) {
            throw new CommitteeDegreeMismatchException();
        }
        lecturers.add((T) lecturer);
        lecturer.addCommitteeToLecturer(this);
    }

    public void removeLecturerFromCommittee(Lecturer lecturer) throws CollegeException {
        if (lecturer == null || isLecturerInCommittee(lecturer) == false) {
            throw new LecturerNotInCommitteeException();
        }
        lecturers.remove(lecturer);
        lecturer.removeCommitteeFromLecturer(this);
    }

    public void changeHeadOfCommittee(Lecturer lecturer) throws CollegeException {
        if (lecturer == null) {
            throw new LecturerNotFoundException();
        }
        if (lecturer.getDegree().compareTo(Lecturer.Degree.Doctor) < 0) {
            throw new InvalidCommitteeHeadException();
        }
        if (lecturer.belongsToAnyCommittee() && isLecturerInCommittee(lecturer) == false && isHeadOfCommittee(lecturer) == false) {
            throw new CommitteeHeadAlreadyAssignedException();
        }
        if (isLecturerInCommittee(lecturer)) {
            removeLecturerFromCommittee(lecturer);
        }
        setHeadOfCommittee(lecturer);
    }

    private boolean matchesMemberType(Lecturer lecturer) {
        if (memberType == MemberType.FirstDegree) {
            return lecturer instanceof FirstDegree;
        }
        if (memberType == MemberType.SecondDegree) {
            return lecturer instanceof SecondDegree;
        }
        if (memberType == MemberType.Doctor) {
            return lecturer instanceof Doctor;
        }
        if (memberType == MemberType.Professor) {
            return lecturer instanceof Professor;
        }
        return false;
    }

    @Override
    public Committee<T> clone() {
        Committee<T> cloned = new Committee<T>(this);
        cloned.setName("new-" + name);
        return cloned;
    }

    @Override
    public boolean equals(Object other) {
        if ((other instanceof Committee) == false) {
            return false;
        }
        Committee<?> otherCommittee = (Committee<?>) other;
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
        String info = "Committee name: " + name + "\nHead of committee: " + headName + "\nMember type: " + memberType + "\nLecturers in committee: ";
        for (int i = 0; i < lecturers.size(); i++) {
            info += lecturers.get(i).getName() + ", ";
        }
        return info;
    }
}


