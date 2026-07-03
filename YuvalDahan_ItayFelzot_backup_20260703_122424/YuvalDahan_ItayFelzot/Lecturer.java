package YuvalDahan_ItayFelzot;

public class Lecturer {
    public enum Degree {First, Second, Doctor, Professor}
    private String name;
    private String id;
    private Degree degree;
    private String degreeName;
    private float salary;
    private Department department=null;
    private int amountOfComittees=0;
    private Comittee[] comittees=new Comittee[1];
    public Lecturer(String name, String id, Degree degree, String degreeName, float salary) {
        this.setName(name);
        boolean flag = this.setId(id);
        if (flag == false) {
            this.id = "-1";
        }
        this.setDegree(degree);
        this.setDegreeName(degreeName);
        flag= this.setSalary(salary);
        if (flag == false) {
            this.salary = -1;
        }
    }
    public Lecturer(Lecturer l) {
        if (l!=null)
        {
        this.setName(l.getName());
        this.setId(l.getId());
        this.setDegree(l.getDegree());
        this.setDegreeName(l.getDegreeName());
        this.setSalary(l.getSalary());
        this.setDepartment(l.getDepartment());
        this.setComittees(l.getComittees());
        }
    }
    public void setComittees(Comittee[] comittees) {
        if (comittees == null) 
            return;
        this.comittees = new Comittee[comittees.length];
        int amount=0;
        for (int i = 0; i < comittees.length; i++) {
            this.comittees[i] = comittees[i];
            if (this.comittees[i] != null)
                amount++;
        }
        this.amountOfComittees = amount;
    }
    public void addComitteeToLecturer(Comittee c) {
        if (c == null)
            return;
        for (int i = 0; i < this.amountOfComittees; i++) {
            if (this.comittees[i] != null && this.comittees[i].getName().equals(c.getName()))
                return;
        }
        Comittee[] newComittees;
        if (this.comittees.length == this.amountOfComittees)
            newComittees = new Comittee[Math.max(1, this.amountOfComittees*2)];
        else
            newComittees = new Comittee[this.comittees.length];
        for (int i = 0; i < this.amountOfComittees; i++) {
            newComittees[i] = comittees[i];
        }
        newComittees[this.amountOfComittees] = c;
        this.comittees = newComittees;
        this.amountOfComittees++;
    }
    public boolean removeComitteeFromLecturer(Comittee c) {
        if (c == null)
            return false;
        Comittee[] newComittees = new Comittee[Math.max(1, this.amountOfComittees)];
        int j = 0;
        for (int i = 0; i < this.amountOfComittees; i++) {
            if (this.comittees[i] == null) continue;
            if (!comittees[i].getName().equals(c.getName())) {
                newComittees[j] = comittees[i];
                j++;
            }
        }
        this.comittees = newComittees;
        this.amountOfComittees = j;
        return true;
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
    public void setName(String name) {
        this.name = name;
    }
    public Comittee[] getComittees() {
        return comittees;
    }
    public int getAmountOfComittees() {
        return amountOfComittees;
    }
    public boolean setId(String id) {
        if (id != null && id.length() > 0) {
            for (int i = 0; i < id.length(); i++) {
                if (id.charAt(i) < '0' || id.charAt(i) > '9') {
                    return false;
                }
            }
            this.id = id;
            return true;
        }
        return false;
    }
    public void setDegree(Degree degree) {
        this.degree = degree;
    }
    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }
    public boolean setSalary(float salary) {
        if (salary >= 0) {
            this.salary = salary;
            return true;
        }
        return false;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public String toString() {
        StringBuffer sB= new StringBuffer ("Name: " + name + "\nId: " + id + "\nDegree: " + degree + "\nDegree Name: " + degreeName + "\nSalary: " + salary);
        if (this.department != null) {
            sB.append("\nDepartment: " + department.toString());
        }
        if (this.comittees != null && this.amountOfComittees > 0) {
            sB.append("\nComittees he is in: ");
            for (int i = 0; i < this.amountOfComittees; i++) {
                if (this.comittees[i] != null)
                    sB.append(this.comittees[i].getName()).append(", ");
            }
        }
        return sB.toString();
    }
}