package YuvalDahan_ItayFelzot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class College implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<Department> departmentsArr = new ArrayList<Department>();
    private ArrayList<Committee> committeesArr = new ArrayList<Committee>();
    private ArrayList<Lecturer> lecturersArr = new ArrayList<Lecturer>();

    public College(String name) {
        setName(name);
    }

    public static College loadFromFile(String fileName) throws CollegeException {
        File file = new File(fileName);
        if (file.exists() == false) {
            return null;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object data = in.readObject();
            in.close();
            if ((data instanceof College) == false) {
                throw new CorruptedCollegeFileException();
            }
            return (College) data;
        } catch (ClassNotFoundException e) {
            throw new CorruptedCollegeFileException();
        } catch (IOException e) {
            throw new CollegeFileException();
        }
    }

    public void saveToFile(String fileName) throws CollegeException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            throw new CollegeFileException();
        }
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

    public void inputCommittee(String committeeName, String headOfCommitteeName, int memberTypeInput) throws CollegeException {
        inputCommittee(committeeName, headOfCommitteeName, "", memberTypeInput);
    }

    public void inputCommittee(String committeeName, String headOfCommitteeName, String headOfCommitteeId, int memberTypeInput) throws CollegeException {
        if (findCommitteeByName(committeeName) != null) {
            throw new DuplicateCommitteeException();
        }
        Lecturer headOfCommittee = findLecturerByNameAndId(headOfCommitteeName, headOfCommitteeId);
        if (headOfCommittee.getDegree().ordinal() < Lecturer.Degree.Doctor.ordinal()) {
            throw new InvalidCommitteeHeadException();
        }
        if (headOfCommittee.belongsToAnyCommittee()) {
            throw new CommitteeHeadAlreadyAssignedException();
        }
        Committee.MemberType memberType = createMemberType(memberTypeInput);
        addCommittee(new Committee<Lecturer>(committeeName, headOfCommittee, memberType));
    }

    private Committee.MemberType createMemberType(int memberTypeInput) throws InvalidDegreeException {
        if (memberTypeInput == 1) {
            return Committee.MemberType.FirstDegree;
        }
        if (memberTypeInput == 2) {
            return Committee.MemberType.SecondDegree;
        }
        if (memberTypeInput == 3) {
            return Committee.MemberType.Doctor;
        }
        if (memberTypeInput == 4) {
            return Committee.MemberType.Professor;
        }
        throw new InvalidDegreeException();
    }

    public void addLecturerToCommittee(String lecturerName, String committeeName) throws CollegeException {
        addLecturerToCommittee(lecturerName, "", committeeName);
    }

    public void addLecturerToCommittee(String lecturerName, String lecturerId, String committeeName) throws CollegeException {
        Committee committee = findRequiredCommitteeByName(committeeName);
        Lecturer lecturer = findLecturerByNameAndId(lecturerName, lecturerId);
        committee.addLecturerToCommittee(lecturer);
    }

    public void changeHeadOfCommittee(String committeeName, String lecturerName) throws CollegeException {
        changeHeadOfCommittee(committeeName, lecturerName, "");
    }

    public void changeHeadOfCommittee(String committeeName, String lecturerName, String lecturerId) throws CollegeException {
        Committee committee = findRequiredCommitteeByName(committeeName);
        Lecturer lecturer = findLecturerByNameAndId(lecturerName, lecturerId);
        committee.changeHeadOfCommittee(lecturer);
    }

    public void removeLecturerFromCommittee(String committeeName, String lecturerName) throws CollegeException {
        removeLecturerFromCommittee(committeeName, lecturerName, "");
    }

    public void removeLecturerFromCommittee(String committeeName, String lecturerName, String lecturerId) throws CollegeException {
        Committee committee = findRequiredCommitteeByName(committeeName);
        Lecturer lecturer = findLecturerByNameAndId(lecturerName, lecturerId);
        committee.removeLecturerFromCommittee(lecturer);
    }

    public void inputDepartment(String departmentName) throws CollegeException {
        addDepartment(new Department(departmentName));
    }

    public void addLecturerToDepartment(String lecturerName, String departmentName) throws CollegeException {
        addLecturerToDepartment(lecturerName, "", departmentName);
    }

    public void addLecturerToDepartment(String lecturerName, String lecturerId, String departmentName) throws CollegeException {
        Department department = findRequiredDepartmentByName(departmentName);
        Lecturer lecturer = findLecturerByNameAndId(lecturerName, lecturerId);
        department.addLecturerToDepartment(lecturer);
    }

    public void addArticleToLecturer(String lecturerName, String articleName) throws CollegeException {
        addArticleToLecturer(lecturerName, "", articleName);
    }

    public void addArticleToLecturer(String lecturerName, String lecturerId, String articleName) throws CollegeException {
        ArticleWriter articleWriter = getArticleWriter(lecturerName, lecturerId);
        articleWriter.addArticle(articleName);
    }

    public String compareArticleWriters(String firstLecturerName, String secondLecturerName) throws CollegeException {
        return compareArticleWriters(firstLecturerName, "", secondLecturerName, "");
    }

    public String compareArticleWriters(String firstLecturerName, String firstLecturerId, String secondLecturerName, String secondLecturerId) throws CollegeException {
        ArticleWriter first = getArticleWriter(firstLecturerName, firstLecturerId);
        ArticleWriter second = getArticleWriter(secondLecturerName, secondLecturerId);
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
        committeesArr.add(cloned);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDepartmentInCollege(Department department) {
        if (department == null) {
            return false;
        }
        return departmentsArr.contains(department);
    }

    public void addDepartment(Department department) throws CollegeException {
        if (department == null) {
            throw new InvalidDepartmentException();
        }
        if (isDepartmentInCollege(department)) {
            throw new DuplicateDepartmentException();
        }
        departmentsArr.add(department);
    }

    public boolean isCommitteeInCollege(Committee committee) {
        if (committee == null) {
            return false;
        }
        return committeesArr.contains(committee);
    }

    public void addCommittee(Committee committee) throws CollegeException {
        if (committee == null) {
            throw new InvalidCommitteeException();
        }
        if (isCommitteeInCollege(committee)) {
            throw new DuplicateCommitteeException();
        }
        committeesArr.add(committee);
    }

    public boolean isLecturerInCollege(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        return lecturersArr.contains(lecturer);
    }

    public void addLecturer(Lecturer lecturer) throws CollegeException {
        if (lecturer == null) {
            throw new InvalidLecturerException();
        }
        if (isLecturerInCollege(lecturer)) {
            throw new DuplicateLecturerException();
        }
        lecturersArr.add(lecturer);
    }

    public float averageSalaryOfDepartment(Department department) throws CollegeException {
        if (department == null || isDepartmentInCollege(department) == false) {
            throw new DepartmentNotFoundException();
        }
        ArrayList<Lecturer> lecturers = department.getLecturers();
        float sum = 0;
        for (int i = 0; i < lecturers.size(); i++) {
            sum += lecturers.get(i).getSalary();
        }
        if (lecturers.size() == 0) {
            return 0;
        }
        return sum / lecturers.size();
    }

    public float averageSalaryOfCollege() {
        float sum = 0;
        for (int i = 0; i < lecturersArr.size(); i++) {
            sum += lecturersArr.get(i).getSalary();
        }
        if (lecturersArr.size() == 0) {
            return 0;
        }
        return sum / lecturersArr.size();
    }

    public String lecturersInfo() {
        StringBuffer info = new StringBuffer("\n");
        for (int i = 0; i < lecturersArr.size(); i++) {
            info.append("\n").append(lecturersArr.get(i)).append("\n");
        }
        return info.toString();
    }

    public String committeesInfo() {
        StringBuffer info = new StringBuffer("\n");
        for (int i = 0; i < committeesArr.size(); i++) {
            info.append("\n").append(committeesArr.get(i)).append("\n");
        }
        return info.toString();
    }

    public String departmentsInfo() {
        StringBuffer info = new StringBuffer("\n");
        for (int i = 0; i < departmentsArr.size(); i++) {
            info.append("\n").append(departmentsArr.get(i)).append("\n");
        }
        return info.toString();
    }

    private Lecturer findLecturerByName(String lecturerName) throws CollegeException {
        Lecturer foundLecturer = null;
        int amountWithName = 0;
        for (int i = 0; i < lecturersArr.size(); i++) {
            if (lecturersArr.get(i).getName().equals(lecturerName)) {
                foundLecturer = lecturersArr.get(i);
                amountWithName++;
            }
        }
        if (amountWithName > 1) {
            throw new MultipleLecturersWithSameNameException(lecturerName);
        }
        if (foundLecturer != null) {
            return foundLecturer;
        }
        throw new LecturerNotFoundException();
    }

    private Lecturer findLecturerByNameAndId(String lecturerName, String lecturerId) throws CollegeException {
        if (lecturerId == null || lecturerId.equals("")) {
            return findLecturerByName(lecturerName);
        }
        for (int i = 0; i < lecturersArr.size(); i++) {
            if (lecturersArr.get(i).getName().equals(lecturerName) && lecturersArr.get(i).getId().equals(lecturerId)) {
                return lecturersArr.get(i);
            }
        }
        throw new LecturerNotFoundException();
    }

    private Department findRequiredDepartmentByName(String departmentName) throws DepartmentNotFoundException {
        for (int i = 0; i < departmentsArr.size(); i++) {
            if (departmentsArr.get(i).getName().equals(departmentName)) {
                return departmentsArr.get(i);
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
        for (int i = 0; i < committeesArr.size(); i++) {
            if (committeesArr.get(i).getName().equals(committeeName)) {
                return committeesArr.get(i);
            }
        }
        return null;
    }

    private ArticleWriter getArticleWriter(String lecturerName) throws CollegeException {
        return getArticleWriter(lecturerName, "");
    }

    private ArticleWriter getArticleWriter(String lecturerName, String lecturerId) throws CollegeException {
        Lecturer lecturer = findLecturerByNameAndId(lecturerName, lecturerId);
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
        ArrayList lecturers = committee.getLecturers();
        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i) instanceof ArticleWriter) {
                amount += ((ArticleWriter) lecturers.get(i)).getAmountOfArticles();
            }
        }
        return amount;
    }

    private class CommitteeMembersComparator implements Comparator<Committee>, Serializable {
        private static final long serialVersionUID = 1L;

        public int compare(Committee first, Committee second) {
            return Integer.compare(first.getAmountOfLecturers(), second.getAmountOfLecturers());
        }
    }

    private class CommitteeArticlesComparator implements Comparator<Committee>, Serializable {
        private static final long serialVersionUID = 1L;

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
        return "College name: " + name + "\nDepartments in college:\n" + departmentsInfo() + "\nCommittees in college:\n" + committeesInfo() + "\nLecturers in college:\n" + lecturersInfo();
    }
}
