package YuvalDahan_ItayFelzot;

public class Lecturer {
    public enum Degree {First, Second, Doctor, Professor}
    private String name;
    private String id;
    private Degree degree;
    private String degreeName;
    private float salary;
    private Department department = null;
    private int amountOfCommittees = 0;
    private Committee[] committees = new Committee[1];

    public Lecturer(String name, String id, Degree degree, String degreeName, float salary) throws CollegeException {
        setName(name);
        setId(id);
        setDegree(degree);
        setDegreeName(degreeName);
        setSalary(salary);
    }

    public Lecturer(Lecturer lecturer) throws CollegeException {
        if (lecturer != null) {
            setName(lecturer.getName());
            setId(lecturer.getId());
            setDegree(lecturer.getDegree());
            setDegreeName(lecturer.getDegreeName());
            setSalary(lecturer.getSalary());
            setDepartment(lecturer.getDepartment());
            setCommittees(lecturer.getCommittees());
        }
    }

    public Lecturer() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Degree getDegree() {
        return degree;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public float getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    public Committee[] getCommittees() {
        return committees;
    }

    public int getAmountOfCommittees() {
        return amountOfCommittees;
    }

    public boolean belongsToAnyCommittee() {
        return amountOfCommittees > 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommittees(Committee[] committees) {
        if (committees == null) {
            return;
        }
        this.committees = new Committee[Math.max(1, committees.length)];
        int amount = 0;
        for (int i = 0; i < committees.length; i++) {
            this.committees[i] = committees[i];
            if (this.committees[i] != null) {
                amount++;
            }
        }
        this.amountOfCommittees = amount;
    }

    public void addCommitteeToLecturer(Committee committee) throws CollegeException {
        if (committee == null) {
            throw new InvalidCommitteeException();
        }
        for (int i = 0; i < this.amountOfCommittees; i++) {
            if (this.committees[i] != null && this.committees[i].getName().equals(committee.getName())) {
                throw new LecturerAlreadyInCommitteeException();
            }
        }
        Committee[] newCommittees;
        if (this.committees.length == this.amountOfCommittees) {
            newCommittees = new Committee[Math.max(1, this.amountOfCommittees * 2)];
        } else {
            newCommittees = new Committee[this.committees.length];
        }
        for (int i = 0; i < this.amountOfCommittees; i++) {
            newCommittees[i] = committees[i];
        }
        newCommittees[this.amountOfCommittees] = committee;
        this.committees = newCommittees;
        this.amountOfCommittees++;
    }

    public void removeCommitteeFromLecturer(Committee committee) throws CollegeException {
        if (committee == null) {
            throw new InvalidCommitteeException();
        }
        boolean found = false;
        Committee[] newCommittees = new Committee[Math.max(1, this.amountOfCommittees)];
        int j = 0;
        for (int i = 0; i < this.amountOfCommittees; i++) {
            if (this.committees[i] == null) {
                continue;
            }
            if (!this.committees[i].getName().equals(committee.getName())) {
                newCommittees[j] = committees[i];
                j++;
            } else {
                found = true;
            }
        }
        if (!found) {
            throw new LecturerNotInCommitteeException();
        }
        this.committees = newCommittees;
        this.amountOfCommittees = j;
    }

    public void setId(String id) throws InvalidIdException {
        if (id != null && id.length() > 0) {
            for (int i = 0; i < id.length(); i++) {
                if (id.charAt(i) < '0' || id.charAt(i) > '9') {
                    throw new InvalidIdException();
                }
            }
            this.id = id;
            return;
        }
        throw new InvalidIdException();
    }

    public void setDegree(Degree degree) throws InvalidDegreeException {
        if (degree == null) {
            throw new InvalidDegreeException();
        }
        this.degree = degree;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public void setSalary(float salary) throws InvalidSalaryException {
        if (salary < 0) {
            throw new InvalidSalaryException();
        }
        this.salary = salary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Lecturer)) {
            return false;
        }
        Lecturer otherLecturer = (Lecturer) other;
        return id != null && id.equals(otherLecturer.getId());
    }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer("Name: " + name + "\nId: " + id + "\nDegree: " + degree + "\nDegree Name: " + degreeName + "\nSalary: " + salary);
        if (this.department != null) {
            info.append("\nDepartment: ").append(department.getName());
        }
        if (this.committees != null && this.amountOfCommittees > 0) {
            info.append("\nCommittees he is in: ");
            for (int i = 0; i < this.amountOfCommittees; i++) {
                if (this.committees[i] != null) {
                    if (committees[i].getHeadOfCommittee() != null && committees[i].getHeadOfCommittee().equals(this)) {
                        info.append("Head of ");
                    }
                    info.append(this.committees[i].getName()).append(", ");
                }
            }
        }
        return info.toString();
    }
}
