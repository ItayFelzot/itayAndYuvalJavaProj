package YuvalDahan_ItayFelzot;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();

    public Department(String name) {
        setName(name);
    }

    public Department(Department department) {
        setName(department.getName());
        setLecturers(department.getLecturers());
    }

    public String getName() {
        return name;
    }

    public int getAmountOfLecturers() {
        return lecturers.size();
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLecturers(ArrayList<Lecturer> lecturers) {
        this.lecturers = new ArrayList<Lecturer>();
        if (lecturers == null) {
            return;
        }
        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i) != null) {
                this.lecturers.add(lecturers.get(i));
            }
        }
    }

    public boolean isLecturerInDepartment(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        return lecturers.contains(lecturer);
    }

    public void addLecturerToDepartment(Lecturer lecturer) throws CollegeException {
        if (lecturer == null) {
            throw new LecturerNotFoundException();
        }
        if (isLecturerInDepartment(lecturer)) {
            throw new LecturerAlreadyInDepartmentException();
        }
        if (lecturer.getDepartment() != null) {
            lecturer.getDepartment().removeLecturerFromDepartment(lecturer);
        }
        lecturer.setDepartment(this);
        lecturers.add(lecturer);
    }

    public void removeLecturerFromDepartment(Lecturer lecturer) throws CollegeException {
        if (lecturer == null || lecturer.getDepartment() != this) {
            throw new LecturerNotInDepartmentException();
        }
        if (lecturers.remove(lecturer) == false) {
            throw new LecturerNotInDepartmentException();
        }
        lecturer.setDepartment(null);
    }

    @Override
    public boolean equals(Object other) {
        if ((other instanceof Department) == false) {
            return false;
        }
        Department otherDepartment = (Department) other;
        return name != null && name.equals(otherDepartment.getName());
    }

    @Override
    public String toString() {
        String str = "\nDepartment name: " + name + "\nLecturers in department: ";
        for (int i = 0; i < lecturers.size(); i++) {
            str += lecturers.get(i).getName() + ", ";
        }
        return str;
    }
}
