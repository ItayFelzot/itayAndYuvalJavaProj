package YuvalDahan_ItayFelzot;

import java.util.Comparator;

public class College {
    private String name;
    private Department[] departmentsArr = new Department[1];
    private int amountOfDepartments = 0;
    private Committee[] committeesArr = new Committee[1];
    private int amountOfCommittees = 0;
    private Lecturer[] lecturersArr = new Lecturer[1];
    private int amountOfLecturers = 0;

    public College(String name) {
        setName(name);
    }

    public void inputLecturer(String name, String id, int degreeInput, String degreeName, float salary, String university) throws CollegeException {
        Lecturer lecturer;
        switch (degreeInput) {
            case 1:
                lecturer = new FirstDegree(name, id, Lecturer.Degree.First, degreeName, salary);
                break;
            case 2:
                lecturer = new SecondDegree(name, id, Lecturer.Degree.Second, degreeName, salary);
                break;
            case 3:
                lecturer = new Doctor(name, id, Lecturer.Degree.Doctor, degreeName, salary);
                break;
            case 4:
                lecturer = new Professor(name, id, Lecturer.Degree.Professor, degreeName, salary, university);
                break;
            default:
                throw new InvalidDegreeException();
        }
        addLecturer(lecturer);
    }

    public void inputCommittee(String committeeName, String headOfCommitteeName) throws CollegeException {
        if (findCommitteeByName(committeeName) != null) {
            throw new DuplicateCommitteeException();
        }
        Lecturer headOfCommittee = findLecturerByName(headOfCommitteeName);
        if (headOfCommittee.getDegree().ordinal() < Lecturer.Degree.Doctor.ordinal()) {
            throw new InvalidCommitteeHeadException();
        }
        if (headOfCommittee.belongsToAnyCommittee()) {
            throw new CommitteeHeadAlreadyAssignedException();
        }
        addCommittee(new Committee(committeeName, headOfCommittee));
    }

    public void addLecturerToCommittee(String lecturerName, String committeeName) throws CollegeException {
        Committee committee = findRequiredCommitteeByName(committeeName);
        Lecturer lecturer = findLecturerByName(lecturerName);
        committee.addLecturerToCommittee(lecturer);
    }

    public void changeHeadOfCommittee(String committeeName, String lecturerName) throws CollegeException {
        Committee committee = findRequiredCommitteeByName(committeeName);
        Lecturer lecturer = findLecturerByName(lecturerName);
        committee.changeHeadOfCommittee(lecturer);
    }

    public void removeLecturerFromCommittee(String committeeName, String lecturerName) throws CollegeException {
        Committee committee = findRequiredCommitteeByName(committeeName);
        Lecturer lecturer = findLecturerByName(lecturerName);
        committee.removeLecturerFromCommittee(lecturer);
    }

    public void inputDepartment(String departmentName) throws CollegeException {
        addDepartment(new Department(departmentName));
    }

    public void addLecturerToDepartment(String lecturerName, String departmentName) throws CollegeException {
        Department department = findRequiredDepartmentByName(departmentName);
        Lecturer lecturer = findLecturerByName(lecturerName);
        department.addLecturerToDepartment(lecturer);
    }

    public void addArticleToLecturer(String lecturerName, String articleName) throws CollegeException {
        ArticleWriter articleWriter = getArticleWriter(lecturerName);
        articleWriter.addArticle(articleName);
    }

    public String compareArticleWriters(String firstLecturerName, String secondLecturerName) throws CollegeException {
        ArticleWriter first = getArticleWriter(firstLecturerName);
        ArticleWriter second = getArticleWriter(secondLecturerName);
        int result = first.compareArticles(second);
        if (first.equals(second)) {
            return "Both lecturers have the same amount of articles: " + first.getAmountOfArticles();
        }
        ArticleWriter winner;
        if (result > 0) {
            winner = first;
        } else {
            winner = second;
        }
        return winner.getName() + " has more articles (" + winner.getAmountOfArticles() + ").";
    }

    public String compareCommittees(String firstCommitteeName, String secondCommitteeName, int compareType) throws CollegeException {
        Committee first = findRequiredCommitteeByName(firstCommitteeName);
        Committee second = findRequiredCommitteeByName(secondCommitteeName);
        Comparator<Committee> comparator;
        if (compareType == 1) {
            comparator = new CommitteeMembersComparator();
        } else if (compareType == 2) {
            comparator = new CommitteeArticlesComparator();
        } else {
            throw new InvalidCompareTypeException();
        }
        int result = comparator.compare(first, second);
        if (result == 0) {
            return "Both committees are equal by this comparison.";
        }
        Committee winner;
        if (result > 0) {
            winner = first;
        } else {
            winner = second;
        }
        return winner.getName() + " is greater by this comparison.";
    }

    public void cloneCommitteeByName(String committeeName) throws CollegeException {
        Committee original = findRequiredCommitteeByName(committeeName);
        Committee cloned = original.clone();
        if (isCommitteeInCollege(cloned)) {
            throw new DuplicateCommitteeException();
        }
        appendCommittee(cloned);
    }

    public float showAverageSalaryOfLecturersInCollege() {
        return averageSalaryOfCollege();
    }

    public float showAverageSalaryOfLecturersInDepartment(String departmentName) throws CollegeException {
        Department department = findRequiredDepartmentByName(departmentName);
        return averageSalaryOfDepartment(department);
    }

    public String showInformationOfAllLecturersInCollege() {
        return lecturersInfo();
    }

    public String showInformationOfAllCommitteesInCollege() {
        return committeesInfo();
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDepartmentInCollege(Department department) {
        if (department == null) {
            return false;
        }
        for (int i = 0; i < this.amountOfDepartments; i++) {
            if (departmentsArr[i] != null && departmentsArr[i].equals(department)) {
                return true;
            }
        }
        return false;
    }

    public void addDepartment(Department department) throws CollegeException {
        if (department == null) {
            throw new InvalidDepartmentException();
        }
        if (isDepartmentInCollege(department)) {
            throw new DuplicateDepartmentException();
        }
        Department[] newDepartmentsArr;
        if (this.departmentsArr.length == this.amountOfDepartments) {
            newDepartmentsArr = new Department[Math.max(1, this.amountOfDepartments * 2)];
        } else {
            newDepartmentsArr = new Department[this.departmentsArr.length];
        }
        for (int i = 0; i < this.amountOfDepartments; i++) {
            newDepartmentsArr[i] = departmentsArr[i];
        }
        newDepartmentsArr[this.amountOfDepartments] = department;
        this.departmentsArr = newDepartmentsArr;
        this.amountOfDepartments++;
    }

    public boolean isCommitteeInCollege(Committee committee) {
        if (committee == null) {
            return false;
        }
        for (int i = 0; i < this.amountOfCommittees; i++) {
            if (committeesArr[i] != null && committeesArr[i].equals(committee)) {
                return true;
            }
        }
        return false;
    }

    public void addCommittee(Committee committee) throws CollegeException {
        if (committee == null) {
            throw new InvalidCommitteeException();
        }
        if (isCommitteeInCollege(committee)) {
            throw new DuplicateCommitteeException();
        }
        appendCommittee(committee);
    }

    private void appendCommittee(Committee committee) {
        Committee[] newCommitteesArr;
        if (this.committeesArr.length == this.amountOfCommittees) {
            newCommitteesArr = new Committee[Math.max(1, this.amountOfCommittees * 2)];
        } else {
            newCommitteesArr = new Committee[this.committeesArr.length];
        }
        for (int i = 0; i < this.amountOfCommittees; i++) {
            newCommitteesArr[i] = committeesArr[i];
        }
        newCommitteesArr[this.amountOfCommittees] = committee;
        this.committeesArr = newCommitteesArr;
        this.amountOfCommittees++;
    }

    public boolean isLecturerInCollege(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        for (int i = 0; i < this.amountOfLecturers; i++) {
            if (lecturersArr[i] != null && lecturersArr[i].getId().equals(lecturer.getId())) {
                return true;
            }
        }
        return false;
    }

    public void addLecturer(Lecturer lecturer) throws CollegeException {
        if (lecturer == null) {
            throw new InvalidLecturerException();
        }
        if (isLecturerInCollege(lecturer)) {
            throw new DuplicateLecturerException();
        }
        Lecturer[] newLecturersArr;
        if (this.lecturersArr.length == this.amountOfLecturers) {
            newLecturersArr = new Lecturer[Math.max(1, this.amountOfLecturers * 2)];
        } else {
            newLecturersArr = new Lecturer[this.lecturersArr.length];
        }
        for (int i = 0; i < this.amountOfLecturers; i++) {
            newLecturersArr[i] = lecturersArr[i];
        }
        newLecturersArr[this.amountOfLecturers] = lecturer;
        this.lecturersArr = newLecturersArr;
        this.amountOfLecturers++;
    }

    public float averageSalaryOfDepartment(Department department) throws CollegeException {
        if (department == null || isDepartmentInCollege(department) == false) {
            throw new DepartmentNotFoundException();
        }
        Lecturer[] lecturers = department.getLecturers();
        int amount = department.getAmountOfLecturers();
        float sum = 0;
        for (int i = 0; i < amount; i++) {
            if (lecturers[i] != null) {
                sum += lecturers[i].getSalary();
            }
        }
        if (amount == 0) {
            return 0;
        }
        return sum / amount;
    }

    public float averageSalaryOfCollege() {
        float sum = 0;
        for (int i = 0; i < amountOfLecturers; i++) {
            sum += lecturersArr[i].getSalary();
        }
        if (amountOfLecturers == 0) {
            return 0;
        }
        return sum / amountOfLecturers;
    }

    public String lecturersInfo() {
        StringBuffer info = new StringBuffer("\n");
        for (int i = 0; i < amountOfLecturers; i++) {
            info.append("\n").append(lecturersArr[i]).append("\n");
        }
        return info.toString();
    }

    public String committeesInfo() {
        StringBuffer info = new StringBuffer("\n");
        for (int i = 0; i < amountOfCommittees; i++) {
            info.append("\n").append(committeesArr[i]).append("\n");
        }
        return info.toString();
    }

    public String departmentsInfo() {
        StringBuffer info = new StringBuffer("\n");
        for (int i = 0; i < amountOfDepartments; i++) {
            info.append("\n").append(departmentsArr[i]).append("\n");
        }
        return info.toString();
    }

    private Lecturer findLecturerByName(String lecturerName) throws LecturerNotFoundException {
        for (int i = 0; i < amountOfLecturers; i++) {
            if (lecturersArr[i] != null && lecturersArr[i].getName().equals(lecturerName)) {
                return lecturersArr[i];
            }
        }
        throw new LecturerNotFoundException();
    }

    private Department findRequiredDepartmentByName(String departmentName) throws DepartmentNotFoundException {
        for (int i = 0; i < amountOfDepartments; i++) {
            if (departmentsArr[i] != null && departmentsArr[i].getName().equals(departmentName)) {
                return departmentsArr[i];
            }
        }
        throw new DepartmentNotFoundException();
    }

    private Committee findRequiredCommitteeByName(String committeeName) throws CommitteeNotFoundException {
        Committee committee = findCommitteeByName(committeeName);
        if (committee == null) {
            throw new CommitteeNotFoundException();
        }
        return committee;
    }

    private Committee findCommitteeByName(String committeeName) {
        for (int i = 0; i < amountOfCommittees; i++) {
            if (committeesArr[i] != null && committeesArr[i].getName().equals(committeeName)) {
                return committeesArr[i];
            }
        }
        return null;
    }

    private ArticleWriter getArticleWriter(String lecturerName) throws CollegeException {
        Lecturer lecturer = findLecturerByName(lecturerName);
        if ((lecturer instanceof ArticleWriter) == false) {
            throw new InvalidArticleWriterException();
        }
        return (ArticleWriter) lecturer;
    }

    private int countCommitteeArticles(Committee committee) {
        int amount = 0;
        if (committee.getHeadOfCommittee() instanceof ArticleWriter) {
            amount += ((ArticleWriter) committee.getHeadOfCommittee()).getAmountOfArticles();
        }
        Lecturer[] lecturers = committee.getLecturers();
        for (int i = 0; i < committee.getAmountOfLecturers(); i++) {
            if (lecturers[i] instanceof ArticleWriter) {
                amount += ((ArticleWriter) lecturers[i]).getAmountOfArticles();
            }
        }
        return amount;
    }

    private class CommitteeMembersComparator implements Comparator<Committee> {
        public int compare(Committee first, Committee second) {
            return Integer.compare(first.getAmountOfLecturers(), second.getAmountOfLecturers());
        }
    }

    private class CommitteeArticlesComparator implements Comparator<Committee> {
        public int compare(Committee first, Committee second) {
            return Integer.compare(countCommitteeArticles(first), countCommitteeArticles(second));
        }
    }

    @Override
    public boolean equals(Object other) {
        if ((other instanceof College) == false) {
            return false;
        }
        College otherCollege = (College) other;
        return name != null && name.equals(otherCollege.name);
    }

    @Override
    public String toString() {
        return "College name: " + this.name + "\nDepartments in college:\n" + departmentsInfo() + "\nCommittees in college:\n" + committeesInfo() + "\nLecturers in college:\n" + lecturersInfo();
    }
}
