package YuvalDahan_ItayFelzot;
public class Comittee {
    private String name;
    private Lecturer[] lecturers = new Lecturer[1];
    private int amountOfLecturers = 0;
    private Lecturer headOfComittee;
    public Comittee(String name, Lecturer headOfComittee) {
        this.setName(name);
        this.setHeadOfComittee(headOfComittee);
    }
    public Comittee(Comittee c) {
        this.setName(c.getName());
        this.setLecturers(c.getLecturers());
        this.headOfComittee = c.getHeadOfComittee();
    }
    public String getName() {
        return name;
    }
    public Lecturer[] getLecturers() {
        return lecturers;
    }
    public Lecturer getHeadOfComittee() {
        return headOfComittee;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int amountOfLecturers() {
        return this.amountOfLecturers;
    }
    public void setLecturers(Lecturer[] lecturers) {
        if (lecturers == null)
            return;
        this.lecturers = new Lecturer[lecturers.length];
        int amount=0;
        for (int i = 0; i < lecturers.length; i++) {
            if (lecturers[i] == null) continue;
            this.lecturers[i] = lecturers[i];
            amount++;
        }
        this.amountOfLecturers = amount;
    }
    public void setHeadOfComittee(Lecturer headOfComittee) {
        if (this.headOfComittee != null)
            this.headOfComittee.removeComitteeFromLecturer(this);
        this.headOfComittee = headOfComittee;
        if (headOfComittee != null)
            headOfComittee.addComitteeToLecturer(this);
    }
    public boolean isLecturerInComittee(Lecturer l) {
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
    public boolean removeLecturerFromComittee(Lecturer l) {
        if (l==null || isLecturerInComittee(l) == false)
            return false;
        Lecturer[] newLecturers = new Lecturer[this.amountOfLecturers];
        int j = 0;
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i].getId().equals(l.getId()) == false) {
                newLecturers[j] = lecturers[i];
                j++;
            }
        }
        l.removeComitteeFromLecturer(this);
        this.lecturers = newLecturers;
        this.amountOfLecturers = j;
        return true;
    }
    public boolean isheadOfComittee(Lecturer l) {
        if (l==null || this.headOfComittee == null)
            return false;
        String hId = this.headOfComittee.getId();
        String lId = l.getId();
        if (hId != null && lId != null && hId.equals(lId))
            return true;
        return false;
    }
    public boolean addLecturerToComittee(Lecturer l) {
        if (l == null) return false;
        if (isLecturerInComittee(l) || isheadOfComittee(l)) {
            return false;
        }
        Lecturer[] newLecturers;
        if (amountOfLecturers == lecturers.length)
            newLecturers = new Lecturer[Math.max(1, lecturers.length*2)];
        else
            newLecturers = new Lecturer[lecturers.length];
        for (int i = 0; i < this.amountOfLecturers; i++)
            newLecturers[i] = lecturers[i];
        newLecturers[this.amountOfLecturers] = l;
        this.lecturers = newLecturers;
        this.amountOfLecturers++;
        l.addComitteeToLecturer(this);
        return true;
    }
    public boolean changeHeadOfComittee(Lecturer l) {
        if (l == null)
            return false;
        // require degree Doctor or higher (Doctor, Professor)
        if (l.getDegree().compareTo(Lecturer.Degree.Doctor) < 0)
            return false;
        if (isLecturerInComittee(l) == true)
            removeLecturerFromComittee(l);
        setHeadOfComittee(l);
        return true;
    }
    public String toString() {
        String s = "Comittee name: " + this.name + "\nHead of comittee: " + this.headOfComittee.getName() + "\nLecturers in comittee:";
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturers[i] != null) {
                s += lecturers[i].getName() + ", ";
            }
        }
        return s;
    }
}
