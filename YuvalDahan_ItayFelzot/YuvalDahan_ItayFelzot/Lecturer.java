package YuvalDahan_ItayFelzot;

import java.io.Serializable;
import java.util.ArrayList;

public class Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Degree {First, Second, Doctor, Professor}
    private String name;
    private String id;
    private Degree degree;
    private String degreeName;
    private float salary;
    private Department department = null;
    private ArrayList<Committee<? extends Lecturer>> committees = new ArrayList<Committee<? extends Lecturer>>();

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

    public ArrayList<Committee<? extends Lecturer>> getCommittees() {
        return committees;
    }

    public int getAmountOfCommittees() {
        return committees.size();
    }

    public boolean belongsToAnyCommittee() {
        return committees.size() > 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommittees(ArrayList<Committee<? extends Lecturer>> committees) {
        this.committees = new ArrayList<Committee<? extends Lecturer>>();
        if (committees == null) {
            return;
        }
        for (int i = 0; i < committees.size(); i++) {
            if (committees.get(i) != null) {
                this.committees.add(committees.get(i));
            }
        }
    }

    public void addCommitteeToLecturer(Committee<? extends Lecturer> committee) throws CollegeException {
        if (committee == null) {
            throw new InvalidCommitteeException();
        }
        if (committees.contains(committee)) {
            throw new LecturerAlreadyInCommitteeException();
        }
        committees.add(committee);
    }

    public void removeCommitteeFromLecturer(Committee<? extends Lecturer> committee) throws CollegeException {
        if (committee == null) {
            throw new InvalidCommitteeException();
        }
        if (committees.remove(committee) == false) {
            throw new LecturerNotInCommitteeException();
        }
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
        if ((other instanceof Lecturer) == false) {
            return false;
        }
        Lecturer otherLecturer = (Lecturer) other;
        return id != null && id.equals(otherLecturer.getId());
    }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer("Name: " + name + "\nId: " + id + "\nDegree: " + degree + "\nDegree Name: " + degreeName + "\nSalary: " + salary);
        if (department != null) {
            info.append("\nDepartment: ").append(department.getName());
        }
        if (committees.size() > 0) {
            info.append("\nCommittees he is in: ");
            for (int i = 0; i < committees.size(); i++) {
                if (committees.get(i) != null) {
                    info.append(committees.get(i).getName()).append(", ");
                }
            }
        }
        return info.toString();
    }
}
