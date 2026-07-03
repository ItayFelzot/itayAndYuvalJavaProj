package YuvalDahan_ItayFelzot;
public class College {
    private String name;
    private Department[] departmentsArr = new Department[1];
    private int amountOfDepartments = 0;
    private Comittee[] comitteesArr = new Comittee[1];
    private int amountOfComittees = 0;
    private Lecturer[] lecturersArr = new Lecturer[1];
    private int amountOfLecturers = 0;
    public College(String name) {
        this.setName(name);
    }
    public boolean inputLecturer(String name, String id, int degreeInput, String degreeName, float salary) {
        Lecturer.Degree degree;
        switch (degreeInput) {
            case 1:
                degree = Lecturer.Degree.First;
                break;
            case 2:
                degree = Lecturer.Degree.Second;
                break;
            case 3:
                degree = Lecturer.Degree.Doctor;
                break;
            case 4:
                degree = Lecturer.Degree.Professor;
                break;
            default:
                degree = Lecturer.Degree.First;
        }
        Lecturer l = new Lecturer(name, id, degree, degreeName, salary);
        if (l.setId(id) == false || l.setSalary(salary) == false)
            return false;
        return addLecturer(l);
    }
    public int inputComittee(String committeeName, String headOfCommitteeName) {
        Lecturer headOfCommittee = null;
        for (int i = 0; i < amountOfLecturers; i++) {
            if (lecturersArr[i].getName().equals(headOfCommitteeName)) {
                headOfCommittee = lecturersArr[i];
                break;
            }
        }
        if (headOfCommittee == null || isLecturerInCollege(headOfCommittee) == false || headOfCommittee.getDegree().ordinal() < Lecturer.Degree.Doctor.ordinal())
            return 1;
        Comittee c = new Comittee(committeeName, headOfCommittee);
        return addComittee(c);
    }
    public int ltoC(String lecturerName, String committeeName) {
        Lecturer l = null;
        for (int i = 0; i < amountOfLecturers; i++) {
            if (lecturersArr[i].getName().equals(lecturerName)) {
                l = lecturersArr[i];
                break;
            }
        }
        Comittee c = null;
        for (int i = 0; i < amountOfComittees; i++) {
            if (comitteesArr[i].getName().equals(committeeName)) {
                c = comitteesArr[i];
                break;
            }
        }
        return addLecturerToComittee(l, c);
    }
    public int changeHeadOfComittee(String committeeName, String lecturerName) {
        Comittee c = null;
        for (int i = 0; i < amountOfComittees; i++) {
            if (comitteesArr[i].getName().equals(committeeName)) {
                c = comitteesArr[i];
                break;
            }
        }
        Lecturer l = null;
        for (int i = 0; i < amountOfLecturers; i++) {
            if (lecturersArr[i].getName().equals(lecturerName)) {
                l = lecturersArr[i];
                break;
            }
        }
        return changeHeadOfComittee(c, l);
    }
    public int removeLecturerFromComittee(String committeeName, String lecturerName) {
        Comittee c = null;
        for (int i = 0; i < amountOfComittees; i++) {
            if (comitteesArr[i].getName().equals(committeeName)) {
                c = comitteesArr[i];
                break;
            }
        }
        Lecturer l = null;
        for (int i = 0; i < amountOfLecturers; i++) {
            if (lecturersArr[i].getName().equals(lecturerName)) {
                l = lecturersArr[i];
                break;
            }
        }
        return removeLecturerFromComittee(l, c);
    }
    public boolean inputDepartment(String departmentName) {
        Department d = new Department(departmentName);
        return addDepartment(d);
    }
    public int addLecturerToDepartment(String lecturerName, String departmentName) {
        Lecturer l = null;
        for (int i = 0; i < amountOfLecturers; i++) {
            if (lecturersArr[i].getName().equals(lecturerName)) {
                l = lecturersArr[i];
                break;
            }
        }
        Department d = null;
        for (int i = 0; i < amountOfDepartments; i++) {
            if (departmentsArr[i].getName().equals(departmentName)) {
                d = departmentsArr[i];
                break;
            }
        }
        return addLecturerToDepartment(l, d);
    }
    public float showAverageSalaryOfLecturersInCollege() {
        return averageSalaryOfCollege();
    }
    public float showAverageSalaryOfLecturersInDepartment(String departmentName) {
        Department d = null;
        for (int i = 0; i < amountOfDepartments; i++) {
            if (departmentsArr[i].getName().equals(departmentName)) {
                d = departmentsArr[i];
                break;
            }
        }
        return averageSalaryOfDepartment(d);
    }
    public String showInformationOfAllLecturersInCollege() {
        return lecturersInfo();
    }
    public String showInformationOfAllComitteesInCollege() {
        return comitteesInfo();
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDepartmentsArr(Department[] departmentsArr) {
        this.departmentsArr = new Department[departmentsArr.length];
        int amount=0;
        for (int i = 0; i < departmentsArr.length; i++) {
            this.departmentsArr[i] = departmentsArr[i];
            if (this.departmentsArr[i] != null)
                amount++;
        }
        this.amountOfDepartments = amount;
    }
    public void setComitteesArr(Comittee[] comitteesArr) {
        this.comitteesArr = new Comittee[comitteesArr.length];
        int amount=0;
        for (int i = 0; i < comitteesArr.length; i++) {
            this.comitteesArr[i] = comitteesArr[i];
            if (this.comitteesArr[i] != null)
                amount++;
        }
        this.amountOfComittees = amount;
    }
    public void setLecturersArr(Lecturer[] lecturersArr) {
        this.lecturersArr = new Lecturer[lecturersArr.length];
        int amount=0;
        for (int i = 0; i < lecturersArr.length; i++) {
            this.lecturersArr[i] = lecturersArr[i];
            if (this.lecturersArr[i] != null)
                amount++;
        }
        this.amountOfLecturers = amount;
    }
    public boolean isDepartmentInCollege(Department d) {
        if (d == null)
            return false;
        for (int i = 0; i < this.amountOfDepartments; i++) {
            if (departmentsArr[i] != null && departmentsArr[i].getName().equals(d.getName())) {
                return true;
            }
        }
        return false;
    }
    public boolean addDepartment(Department d) {
        if (d==null || isDepartmentInCollege(d) == true) {
            return false;
        }
        Department[] newDepartmentsArr;
        if (this.departmentsArr.length == this.amountOfDepartments)
            newDepartmentsArr = new Department[Math.max(1, this.amountOfDepartments*2)];
        else
            newDepartmentsArr = new Department[this.departmentsArr.length];
        for (int i = 0; i < this.amountOfDepartments; i++) {
            newDepartmentsArr[i] = departmentsArr[i];
        }
        newDepartmentsArr[this.amountOfDepartments] = d;
        this.departmentsArr = newDepartmentsArr;
        this.amountOfDepartments++;
        return true;
    }
    public boolean isComitteeInCollege(Comittee c) {
        if (c == null)
            return false;
        for (int i = 0; i < this.amountOfComittees; i++) {
            if (comitteesArr[i] != null && comitteesArr[i].getName().equals(c.getName())) {
                return true;
            }
        }
        return false;
    }
    public int addComittee(Comittee c) { //returns 0 if the comittee is not valid, 1 if condition not met, 2 if the comittee was added successfully
        if (c==null || isComitteeInCollege(c) == true)
            return 0;
        if (c.getLecturers() != null && c.getHeadOfComittee() != null) {
            c.removeLecturerFromComittee(c.getHeadOfComittee());
        }
        Comittee[] newComitteesArr;
        if (this.comitteesArr.length == this.amountOfComittees)
            newComitteesArr = new Comittee[Math.max(1, this.amountOfComittees*2)];
        else
            newComitteesArr = new Comittee[this.comitteesArr.length];
        for (int i = 0; i < this.amountOfComittees; i++) {
            newComitteesArr[i] = comitteesArr[i];
        }
        newComitteesArr[this.amountOfComittees] = c;
        this.comitteesArr = newComitteesArr;
        this.amountOfComittees++;
        return 2;
    }
    public boolean isLecturerInCollege(Lecturer l) {
        if (l == null) return false;
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturersArr[i] == null) continue;
            String id1 = lecturersArr[i].getId();
            String id2 = l.getId();
            if (id1 != null && id2 != null && id1.equals(id2)) {
                return true;
            }
        }
        return false;
    }
    public boolean addLecturer(Lecturer l) {
        if (l==null || isLecturerInCollege(l) == true)
            return false;
        Lecturer[] newLecturersArr;
        if (this.lecturersArr.length == this.amountOfLecturers)
            newLecturersArr = new Lecturer[Math.max(1, this.amountOfLecturers*2)];
        else
            newLecturersArr = new Lecturer[this.lecturersArr.length];
        for (int i = 0; i < this.amountOfLecturers; i++) {
            newLecturersArr[i] = lecturersArr[i];
        }
        newLecturersArr[this.amountOfLecturers] = l;
        this.lecturersArr = newLecturersArr;
        this.amountOfLecturers++;
        return true;
    }
    public int changeHeadOfComittee(Comittee c, Lecturer l) { //returns 0 if the comittee is not in the college, 1 if the lecturer is not in the college
    //2 if the head of comittee was changed successfully, 3 if required degree is not met
        if (c==null || isComitteeInCollege(c) == false)
            return 0;
        if (l==null || isLecturerInCollege(l) == false)
            return 1;
        for (int i = 0; i < amountOfComittees; i++) {
            if (comitteesArr[i] != null && comitteesArr[i].getName().equals(c.getName())) {
                if (comitteesArr[i].changeHeadOfComittee(l))
                    return 2;
                else
                    return 3;
            }
        }
        return -1;
    }
    public int addLecturerToComittee(Lecturer l, Comittee c) { //returns 0 if the comittee is not in the college, 1 if the lecturer is not in the college
    // 2 if the lecturer was added successfully, 3 if the lecturer is already in the comittee or is the head of the comittee
        if (c==null || isComitteeInCollege(c) == false)
            return 0;
        if (l==null || isLecturerInCollege(l) == false)
            return 1;
        for (int i = 0; i < amountOfComittees; i++) {
            if (comitteesArr[i] != null && comitteesArr[i].getName().equals(c.getName())) {
                if (comitteesArr[i].addLecturerToComittee(l))
                    return 2;
                else
                    return 3;
            }
        }
        return -1;
    }
    public int removeLecturerFromComittee(Lecturer l, Comittee c) { //returns 0 if the comittee is not in the college, 1 if the lecturer is not in the college
    //  2 if the lecturer was removed successfully
        if (c==null || isComitteeInCollege(c) == false)
            return 0;
        if (l==null || isLecturerInCollege(l) == false)
            return 1;
        for (int i = 0; i < amountOfComittees; i++) {
            if (comitteesArr[i] != null && comitteesArr[i].getName().equals(c.getName())) {
                if (comitteesArr[i].removeLecturerFromComittee(l))
                    return 2;
                else
                    return -1;
            }
        }
        return -1;
    }
    public int addLecturerToDepartment(Lecturer l, Department d) { //returns 0 if the department is not in the college, 1 if the lecturer is not in the college
    //2 if the lecturer was added successfully
        if (d==null || isDepartmentInCollege(d) == false)
            return 0;
        if (l==null || isLecturerInCollege(l) == false)
            return 1;
        for (int i = 0; i < amountOfDepartments; i++) {
            if (departmentsArr[i] != null && departmentsArr[i].getName().equals(d.getName())) {
                if (departmentsArr[i].addLecturerToDepartment(l))
                    return 2;
                else
                    return -1;
            }
        }
        return -1;
    }
    public int addLecturerToDepartmentByName(Lecturer l, String departmentName) { //returns -1 if the department is not in the college, 0 if he has no department,
    //1 if the lecturer is not in the college, 2 if the lecturer was added successfully
        if (departmentName == null)
            return 0;
        if (l==null || isLecturerInCollege(l) == false)
            return 1;
        for (int i = 0; i < amountOfDepartments; i++) {
            if (departmentsArr[i] != null && departmentsArr[i].getName().equals(departmentName)) {
                if (departmentsArr[i].addLecturerToDepartment(l))
                    return 2;
                else
                    return -1;
            }
        }
        return -1;
    }
    public float averageSalaryOfDepartment(Department d) { //returns -1 if the department is not in the college, otherwise returns the average salary of the lecturers in the department
        if (d==null || isDepartmentInCollege(d) == false)
            return -1;
        for (int i = 0; i < amountOfDepartments; i++) {
            if (departmentsArr[i] != null && departmentsArr[i].getName().equals(d.getName())) {
                Lecturer[] lecturers = departmentsArr[i].getLecturers();
                int amount = departmentsArr[i].getAmountOfLecturers();
                float sum = 0;
                for (int j = 0; j < amount; j++) {
                    if (lecturers[j] != null)
                        sum += lecturers[j].getSalary();
                }
                if (amount == 0)
                    return 0;
                return (sum/amount);
            }
        }
        return -1;
    }
    public float averageSalaryOfCollege() { //returns the average salary of all the lecturers in the college
        float sum = 0;
        for (int i = 0; i < amountOfLecturers; i++) {
            sum += lecturersArr[i].getSalary();
        }
        if (amountOfLecturers == 0)
            return 0;
        return (sum/amountOfLecturers);
    }
    public String lecturersInfo() {
        StringBuffer info = new StringBuffer('\n');
        for (int i = 0; i < amountOfLecturers; i++) {
            info.append("\n").append(lecturersArr[i].toString()).append("\n");
        }
        return info.toString();
    }
    public String comitteesInfo() {
        StringBuffer info = new StringBuffer('\n');
        for (int i = 0; i < amountOfComittees; i++) {
            info.append("\n").append(comitteesArr[i].toString()).append("\n");
        }
        return info.toString();
    }
    public String departmentsInfo() { //just in case
        StringBuffer info = new StringBuffer('\n');
        for (int i = 0; i < amountOfDepartments; i++) {
            info.append("\n").append(departmentsArr[i].toString()).append("\n");
        }
        return info.toString();
    }
    public String toString() {
        return "College name: " + this.name + "\nDepartments in college:\n" + departmentsInfo() + "\nComittees in college:\n" + comitteesInfo() + "\nLecturers in college:\n" + lecturersInfo();
    }
}
