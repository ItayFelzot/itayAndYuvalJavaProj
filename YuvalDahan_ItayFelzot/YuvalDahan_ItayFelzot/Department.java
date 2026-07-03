package YuvalDahan_ItayFelzot;

public class Department {
    private String name;
    private int amountOfLecturers = 0;
    private Lecturer[] lecturers = new Lecturer[1];

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
        return amountOfLecturers;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
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
            this.lecturers[i] = lecturers[i];
            if (lecturers[i] != null) {
                amount++;
            }
        }
        this.amountOfLecturers = amount;
    }

    public boolean isLecturerInDepartment(Lecturer lecturer) {
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
        Lecturer[] newLecturers;
        if (this.lecturers.length == this.amountOfLecturers) {
            newLecturers = new Lecturer[Math.max(1, this.amountOfLecturers * 2)];
        } else {
            newLecturers = new Lecturer[this.lecturers.length];
        }
        for (int i = 0; i < this.amountOfLecturers; i++) {
            newLecturers[i] = lecturers[i];
        }
        lecturer.setDepartment(this);
        newLecturers[this.amountOfLecturers] = lecturer;
        this.lecturers = newLecturers;
        this.amountOfLecturers++;
    }

    public void removeLecturerFromDepartment(Lecturer lecturer) throws CollegeException {
        if (lecturer == null || lecturer.getDepartment() != this) {
            throw new LecturerNotInDepartmentException();
        }
        Lecturer[] newLecturers = new Lecturer[Math.max(1, this.amountOfLecturers)];
        int j = 0;
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (this.lecturers[i] == null) {
                continue;
            }
            if (!this.lecturers[i].equals(lecturer)) {
                newLecturers[j] = this.lecturers[i];
                j++;
            }
        }
        lecturer.setDepartment(null);
        this.lecturers = newLecturers;
        this.amountOfLecturers = j;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Department)) {
            return false;
        }
        Department otherDepartment = (Department) other;
        return name != null && name.equals(otherDepartment.getName());
    }

    @Override
    public String toString() {
        String str = "\nDepartment name: " + this.name + "\nLecturers in department: ";
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i] != null) {
                str += lecturers[i].getName() + ", ";
            }
        }
        return str;
    }
}
