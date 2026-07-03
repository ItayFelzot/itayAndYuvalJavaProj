package YuvalDahan_ItayFelzot;
public class Department {
    private String name;
    private int amountOfLecturers=0;
    private Lecturer[] lecturers=new Lecturer[1];
    public Department(String name) {
        this.setName(name);
    }
    public Department(Department d) {
        this.setName(d.getName());
        this.setLecturers(d.getLecturers());
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
        if (lecturers == null)
            return;
        this.lecturers = new Lecturer[lecturers.length];
        int amount=0;
        for (int i = 0; i < lecturers.length; i++) {
            this.lecturers[i] = lecturers[i];
            if (lecturers[i] != null)
                amount++;
        }
        this.amountOfLecturers = amount;
    }
    public boolean isLecturerInDepartment(Lecturer l) {
            if (l==null)
                return false;
            for (int i = 0; i < this.amountOfLecturers; i++) {
                if (lecturers[i] == null) continue;
                String id1 = lecturers[i].getId();
                String id2 = l.getId();
                if (id1 != null && id2 != null && id1.equals(id2)) {
                    return true;
                }
            }
            return false;
    }
    public boolean addLecturerToDepartment(Lecturer l) {
        if (l==null || isLecturerInDepartment(l) == true) {
            return false;
        }
        if (l.getDepartment() != null) {
            l.getDepartment().removeLecturerFromDepartment(l);
        }
        Lecturer[] newLecturers;
        if (this.lecturers.length == this.amountOfLecturers)
            newLecturers = new Lecturer[Math.max(1, this.amountOfLecturers*2)];
        else
            newLecturers = new Lecturer[this.lecturers.length];
        for (int i = 0; i < this.amountOfLecturers; i++) {
            newLecturers[i] = lecturers[i];
        }
        l.setDepartment(this);
        newLecturers[this.amountOfLecturers] = l;
        this.lecturers = newLecturers;
        this.amountOfLecturers++;
        return true;
    }
    public boolean removeLecturerFromDepartment(Lecturer l) {
        if (l==null || l.getDepartment() != this)
            return false;
        Lecturer[] newLecturers = new Lecturer[Math.max(1, this.amountOfLecturers)];
        int j = 0;
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (this.lecturers[i] == null) continue;
            if (!this.lecturers[i].getId().equals(l.getId())) {
                newLecturers[j] = this.lecturers[i];
                j++;
            }
        }
        l.setDepartment(null);
        this.lecturers = newLecturers;
        this.amountOfLecturers = j;
        return true;
    }
    public String toString() {
        String str = "\nDepartment name: " + this.name + "\nLecturers in department:";
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i] != null)
                str += lecturers[i].getName() + ", ";
        }
        return str;
    }
}
